package thiengo.com.br.blueshoes.view.config.connectiondata

import thiengo.com.br.blueshoes.view.config.ConfigFormActivity
import thiengo.com.br.blueshoes.view.config.ConfigSectionsAdapter

class ConnectDataActivity :
    ConfigFormActivity() {

    override fun getSectionsAdapter()
        = ConfigSectionsAdapter(
            this,
            supportFragmentManager,
            FormEmailFragment(),
            FormPasswordFragment()
        )
}