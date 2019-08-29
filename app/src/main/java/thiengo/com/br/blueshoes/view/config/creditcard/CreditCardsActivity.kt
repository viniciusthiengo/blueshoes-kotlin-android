package thiengo.com.br.blueshoes.view.config.creditcard

import thiengo.com.br.blueshoes.view.config.ConfigFormActivity
import thiengo.com.br.blueshoes.view.config.ConfigSectionsAdapter
import thiengo.com.br.blueshoes.view.config.connectiondata.FormEmailFragment
import thiengo.com.br.blueshoes.view.config.connectiondata.FormPasswordFragment

class CreditCardsActivity :
    ConfigFormActivity() {

    override fun getSectionsAdapter()
        = ConfigSectionsAdapter(
            this,
            supportFragmentManager,
            CreditCardsListFragment(),
            FormCreditCardFragment()
        )
}