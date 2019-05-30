package thiengo.com.br.blueshoes.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header_user_logged.*
import kotlinx.android.synthetic.main.nav_header_user_not_logged.*
import kotlinx.android.synthetic.main.nav_menu.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.data.NavMenuItemsDataBase
import thiengo.com.br.blueshoes.domain.NavMenuItem
import thiengo.com.br.blueshoes.domain.User
import thiengo.com.br.blueshoes.util.NavMenuItemDetailsLookup
import thiengo.com.br.blueshoes.util.NavMenuItemKeyProvider
import thiengo.com.br.blueshoes.util.NavMenuItemPredicate

class MainActivity :
    AppCompatActivity() {


    companion object {
        /*
         * Para debug em LogCat durante o desenvolvimento do
         * projeto.
         * */
        const val LOG = "log-bs"

        const val FRAGMENT_TAG = "frag-tag"
        const val FRAGMENT_ID = "frag-id"
    }

    val user = User(
        "Thiengo Vinícius",
        R.drawable.user,
        true
    )

    lateinit var navMenuItems : List<NavMenuItem>
    lateinit var selectNavMenuItems: SelectionTracker<Long>
    lateinit var navMenuItemsLogged : List<NavMenuItem>
    lateinit var selectNavMenuItemsLogged: SelectionTracker<Long>
    lateinit var navMenu: NavMenuItemsDataBase

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )
        setSupportActionBar( toolbar )

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener( toggle )
        toggle.syncState()

        initNavMenu( savedInstanceState )

        initFragment()
    }

    override fun onSaveInstanceState( outState: Bundle? ) {
        super.onSaveInstanceState( outState )

        /*
         * Para manter o item de menu gaveta selecionado caso
         * haja reconstrução de atividade.
         * */
        selectNavMenuItems.onSaveInstanceState( outState!! )
        selectNavMenuItemsLogged.onSaveInstanceState( outState )
    }

    /*
     * Método de inicialização do menu gaveta, responsável por
     * apresentar o cabeçalho e itens de menu de acordo com o
     * status do usuário (logado ou não).
     * */
    private fun initNavMenu( savedInstanceState: Bundle? ){

        navMenu = NavMenuItemsDataBase( this )
        navMenuItems = navMenu.items
        navMenuItemsLogged = navMenu.itemsLogged

        showHideNavMenuViews()

        initNavMenuItems()
        initNavMenuItemsLogged()

        if( savedInstanceState != null ){
            selectNavMenuItems.onRestoreInstanceState( savedInstanceState )
            selectNavMenuItemsLogged.onRestoreInstanceState( savedInstanceState )
        }
        else{
            /*
             * Verificando se há algum item ID em intent. Caso não,
             * utilize o ID do primeiro item.
             * */
            var fragId = intent?.getIntExtra( FRAGMENT_ID, 0 )
            if(fragId == 0){
                fragId = R.id.item_all_shoes
            }

            /*
             * O primeiro item do menu gaveta deve estar selecionado
             * caso não seja uma reinicialização de tela / atividade
             * ou o envio de um ID especifico de fragmento a ser aberto.
             * O primeiro item aqui é o de ID R.id.item_all_shoes.
             * */
            selectNavMenuItems.select( fragId!!.toLong() )
        }
    }

    /*
     * Método responsável por esconder itens do menu gaveta de
     * acordo com o status do usuário (conectado ou não).
     * */
    private fun showHideNavMenuViews(){
        if( user.status ){ /* Conectado */
            rl_header_user_not_logged.visibility = View.GONE
            fillUserHeaderNavMenu()
        }
        else{  /* Não conectado */
            rl_header_user_logged.visibility = View.GONE
            v_nav_vertical_line.visibility = View.GONE
            rv_menu_items_logged.visibility = View.GONE
        }
    }

    private fun fillUserHeaderNavMenu(){
        if( user.status ) { /* Conectado */
            iv_user.setImageResource(user.image)
            tv_user.text = user.name
        }
    }

    /*
     * Método que inicializa a lista de itens de menu gaveta
     * que estará presente quando o usuário estiver ou não
     * conectado ao aplicativo.
     * */
    private fun initNavMenuItems(){
        rv_menu_items.setHasFixedSize( false )
        rv_menu_items.layoutManager = LinearLayoutManager( this )
        rv_menu_items.adapter = NavMenuItemsAdapter( navMenuItems )

        initNavMenuItemsSelection()
    }

    /*
     * Método responsável por inicializar o objeto de seleção
     * de itens de menu gaveta, seleção dos itens que aparecem
     * para usuário conectado ou não.
     * */
    private fun initNavMenuItemsSelection(){

        selectNavMenuItems = SelectionTracker.Builder<Long>(
            "id-selected-item",
            rv_menu_items,
            NavMenuItemKeyProvider( navMenuItems ),
            NavMenuItemDetailsLookup( rv_menu_items ),
            StorageStrategy.createLongStorage()
        )
        .withSelectionPredicate( NavMenuItemPredicate( this ) )
        .build()

        selectNavMenuItems.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItemsLogged.selection.filter {
                    selectNavMenuItemsLogged.deselect( it )
                }
            }
        )

        (rv_menu_items.adapter as NavMenuItemsAdapter).selectionTracker = selectNavMenuItems
    }

    /*
     * Método que inicializa a parte de lista de itens de menu
     * gaveta que estará presente somente quando o usuário
     * estiver conectado ao aplicativo.
     * */
    private fun initNavMenuItemsLogged(){
        rv_menu_items_logged.setHasFixedSize( true )
        rv_menu_items_logged.layoutManager = LinearLayoutManager( this )
        rv_menu_items_logged.adapter = NavMenuItemsAdapter( navMenuItemsLogged )

        initNavMenuItemsLoggedSelection()
    }

    /*
     * Método responsável por inicializar o objeto de seleção
     * de itens de menu gaveta, seleção dos itens que aparecem
     * somente quando o usuário está conectado ao app.
     * */
    private fun initNavMenuItemsLoggedSelection(){

        selectNavMenuItemsLogged = SelectionTracker.Builder<Long>(
            "id-selected-item-logged",
            rv_menu_items_logged,
            NavMenuItemKeyProvider( navMenuItemsLogged ),
            NavMenuItemDetailsLookup( rv_menu_items_logged ),
            StorageStrategy.createLongStorage()
        )
        .withSelectionPredicate( NavMenuItemPredicate( this ) )
        .build()

        selectNavMenuItemsLogged.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItems.selection.filter {
                    selectNavMenuItems.deselect( it )
                }
            }
        )

        (rv_menu_items_logged.adapter as NavMenuItemsAdapter).selectionTracker = selectNavMenuItemsLogged
    }



    private fun initFragment(){
        val supFrag = supportFragmentManager
        var fragment = supFrag.findFragmentByTag( FRAGMENT_TAG )

        /*
         * Se não for uma reconstrução de atividade, então não
         * haverá um fragmento em memória, então busca-se o
         * inicial.
         * */
        if( fragment == null ){

            /*
             * Caso haja algum ID de fragmento em intent, então
             * é este fragmento que deve ser acionado. Caso
             * contrário, abra o fragmento comum de início.
             * */
            var fragId = intent?.getIntExtra( FRAGMENT_ID, 0 )
            if( fragId == 0 ){
                fragId = R.id.item_about
            }

            fragment = getFragment( fragId!!.toLong() )
        }

        replaceFragment( fragment )
    }

    private fun getFragment( fragId: Long ): Fragment{

        return when( fragId ){
            R.id.item_about.toLong() -> AboutFragment()
            R.id.item_contact.toLong() -> ContactFragment()
            R.id.item_privacy_policy.toLong() -> PrivacyPolicyFragment()
            else -> AboutFragment()
        }
    }

    private fun replaceFragment( fragment: Fragment ){
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_fragment_container,
                fragment,
                FRAGMENT_TAG
            )
            .commit()
    }

    fun updateToolbarTitleInFragment( titleStringId: Int ){
        toolbar.title = getString( titleStringId )
    }



    override fun onBackPressed() {
        if( drawer_layout.isDrawerOpen( GravityCompat.START ) ){
            drawer_layout.closeDrawer( GravityCompat.START )
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu( menu: Menu ): Boolean {
        /*
         * Infla o menu. Adiciona itens a barra de topo, se
         * ela estiver presente.
         * */
        menuInflater.inflate( R.menu.main, menu )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
         * Lidar com cliques de itens da barra de ação aqui.
         * A barra de ação manipulará automaticamente os
         * cliques no botão Home / Up, desde que seja
         * especificada uma atividade pai em AndroidManifest.xml.
         * */
        when( item.itemId ) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected( item )
        }
    }

    fun callLoginActivity( view: View ){
        val intent = Intent( this, LoginActivity::class.java )
        startActivity( intent )
    }

    fun callSignUpActivity( view: View ){
        /*val intent = Intent(
            this,
            SignUpActivity::class.java
        )
        startActivity( intent )*/
    }


    inner class SelectObserverNavMenuItems(
        val callbackRemoveSelection: ()->Unit
    ) :
        SelectionTracker.SelectionObserver<Long>(){

        /*
         * Método responsável por permitir que seja possível
         * disparar alguma ação de acordo com a mudança de
         * status de algum item em algum dos objetos de seleção
         * de itens de menu gaveta. Aqui vamos proceder com
         * alguma ação somente em caso de item obtendo seleção,
         * para item perdendo seleção não haverá processamento,
         * pois este status não importa na lógica de negócio
         * deste método.
         * */
        override fun onItemStateChanged(
            key: Long,
            selected: Boolean ) {
            super.onItemStateChanged( key, selected )

            /*
             * Padrão Cláusula de Guarda para não seguirmos
             * com o processamento em caso de item perdendo
             * seleção. O processamento posterior ao condicional
             * abaixo é somente para itens obtendo a seleção,
             * selected = true.
             * */
            if( !selected ){
                return
            }

            /*
             * Se o item de menu selecionado for um que aciona
             * uma atividade, então a lógica de negócio difere
             * de quando é o acionamento de um fragmento.
             * */
            if( isActivityCallInMenu( key ) ) {
                itemCallActivity( key, callbackRemoveSelection )
            }
            else {
                itemCallFragment( key, callbackRemoveSelection )
            }
        }
    }

    /*
     * Alguns itens do menu gaveta de usuário conectado acionam
     * a abertura de uma atividade e não a abertura de um novo
     * fragmento, dessa forma o método abaixo será útil em
     * lógicas de negócio para informar quais são os itens que
     * acionam atividades.
     * */
    fun isActivityCallInMenu( key: Long )
        = when( key ){
            R.id.item_settings.toLong() -> true
            else -> false
        }

    private fun itemCallActivity(
        key: Long,
        callbackRemoveSelection: ()->Unit
    ){

        callbackRemoveSelection()

        lateinit var intent : Intent

        when( key ){
            R.id.item_settings.toLong() -> {
                intent = Intent(
                    this,
                    AccountSettingsActivity::class.java
                )
                intent.putExtra( User.KEY, user )
            }
        }

        navMenu.saveIsActivityItemFired(
            this,
            true
        )

        startActivity( intent )
    }

    private fun itemCallFragment(
        key: Long,
        callbackRemoveSelection: ()->Unit
    ){
        /*
         * Para garantir que somente um item de lista se
         * manterá selecionado, é preciso acessar o objeto
         * de seleção da lista de itens de usuário conectado
         * para então remover qualquer possível seleção
         * ainda presente nela. Sempre haverá somente um
         * item selecionado, mas infelizmente o método
         * clearSelection() não estava respondendo como
         * esperado, por isso a estratégia a seguir.
         * */
        callbackRemoveSelection()

        navMenu.saveLastSelectedItemFragmentID(
            this,
            key
        )

        if( !navMenu.wasActivityItemFired(this) ){
            /*
             * Somente permiti a real seleção de um fragmento e o
             * fechamento do menu gaveta se o item de menu anterior
             * selecionado não tiver sido um que aciona uma atividade.
             * Caso contrário o fragmento já em tela deve continuar
             * e o menu gaveta deve permanecer aberto.
             * */

            val fragment = getFragment( key )
            replaceFragment( fragment )

            /*
             * Fechando o menu gaveta.
             * */
            drawer_layout.closeDrawer( GravityCompat.START )
        }
        else{
            navMenu.saveIsActivityItemFired(
                this,
                false
            )
        }
    }

    override fun onResume() {
        super.onResume()

        /*
         * Se o último item de menu gaveta selecionado foi um
         * que aciona uma atividade, então temos de colocar a
         * seleção de item correta em menu gaveta, item que
         * estava selecionado antes do acionamento do item que
         * invoca uma atividade.
         * */
        if( navMenu.wasActivityItemFired(this) ){
            selectNavMenuItems.select(
                navMenu.getLastSelectedItemFragmentID(this)
            )
        }
    }
}
