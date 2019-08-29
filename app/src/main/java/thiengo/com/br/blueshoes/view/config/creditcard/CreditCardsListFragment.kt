package thiengo.com.br.blueshoes.view.config.creditcard


import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_config_list.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.data.CreditCardsDataBase
import thiengo.com.br.blueshoes.view.config.ConfigListFragment


class CreditCardsListFragment :
    ConfigListFragment() {

    override fun title()
        = R.string.config_credit_cards_tab_list

    override fun backEndFakeDelay() {
        backEndFakeDelay(
            true,
            getString( R.string.credit_card_removed )
        )
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        tv_empty_list.setText( R.string.credit_card_list_empty )
    }

    override fun getRecyclerViewAdapter()
        = CreditCardsListAdapter(
            this,
            CreditCardsDataBase.getItems()
        )
}
