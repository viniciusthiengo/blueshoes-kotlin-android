package thiengo.com.br.blueshoes.view.config.connectiondata

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_config_connection_data.*
import thiengo.com.br.blueshoes.R

class ConfigConnectionDataActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_config_connection_data )
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
            ConfigConnectionDataSectionsAdapter(
                this,
                supportFragmentManager
            )

        /*
         * Acessando o ViewPager e vinculando o adaptador de
         * fragmentos a ele.
         * */
        val viewPager: ViewPager = findViewById( R.id.view_pager )
        viewPager.adapter = sectionsPagerAdapter

        /*
         * Acessando o TabLayout e vinculando ele ao ViewPager
         * para que haja sincronia na escolha realizada em
         * qualquer um destes componentes visuais.
         * */
        val tabs: TabLayout = findViewById( R.id.tabs )
        tabs.setupWithViewPager( viewPager )
    }

    /*
     * Para permitir que o back button tenha a ação de volta para
     * a atividade anterior.
     * */
    override fun onOptionsItemSelected( item: MenuItem ): Boolean {

        if( item.itemId == android.R.id.home ){
            finish()
            return true
        }
        return super.onOptionsItemSelected( item )
    }
}