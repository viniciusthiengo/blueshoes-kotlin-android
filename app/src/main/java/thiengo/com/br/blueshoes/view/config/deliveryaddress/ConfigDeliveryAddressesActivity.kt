package thiengo.com.br.blueshoes.view.config.deliveryaddress

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tabs_user_config.*
import thiengo.com.br.blueshoes.R


class ConfigDeliveryAddressesActivity :
    AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_tabs_user_config )
        setSupportActionBar( toolbar )

        /*
         * Para liberar o back button na barra de topo da
         * atividade.
         * */
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        supportActionBar?.setDisplayShowHomeEnabled( true )

        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */
        window.setBackgroundDrawableResource( R.drawable.bg_activity )

        /*
         * Criando o adaptador de fragmentos que ficarão expostos
         * no ViewPager.
         * */
        val sectionsPagerAdapter =
            ConfigDeliveryAddressesSectionsAdapter(
                this,
                supportFragmentManager
            )

        /*
         * Acessando o ViewPager e vinculando o adaptador de
         * fragmentos a ele.
         * */
        view_pager.adapter = sectionsPagerAdapter

        /*
         * Acessando o TabLayout e vinculando ele ao ViewPager
         * para que haja sincronia na escolha realizada em
         * qualquer um destes componentes visuais.
         * */
        tabs.setupWithViewPager( view_pager )
    }

    /*
     * Para permitir que o back button tenha a ação de volta para
     * a atividade anterior.
     * */
    override fun onOptionsItemSelected( item: MenuItem ): Boolean {

        if( item.itemId == android.R.id.home ){
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected( item )
    }


    override fun onBackPressed() {
        val fragmentsInStack = supportFragmentManager.backStackEntryCount

        /*
         * Se houver algum fragmento em pilha de fragmentos
         * e o fragmento atual em tela não for o fragment de
         * formulário de novo endereço de entrega, então o
         * próximo fragmento da pilha de fragmentos é que
         * deve ser apresentado.
         *
         * Caso contrário, volte a atividade anterior via
         * finish().
         * */
        if( fragmentsInStack > 0
            && isNewDeliveryAddressFormNotInScreen() ){
            supportFragmentManager.popBackStack()
        }
        else {
            finish()
        }
    }

    private fun isNewDeliveryAddressFormNotInScreen() : Boolean
        = view_pager.currentItem != ConfigNewDeliveryAddressFragment.PAGER_POS
}