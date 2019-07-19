package thiengo.com.br.blueshoes.view.config.creditcard

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Um FragmentPagerAdapter que retorna um fragmento correspondendo
 * a uma das sections/tabs/pages.
 *
 * Mesmo que o método getItem() indique que mais de uma instância
 * do mesmo fragmento será criada, na verdade objetos
 * FragmentPagerAdapter mantêm os fragmentos em memória para que
 * eles possam ser utilizados novamente, isso enquanto houver
 * caminho de volta a eles (transição entre Tabs, por exemplo).
 */
class ConfigCreditCardsSectionsAdapter(
    val context: Context,
    fm: FragmentManager ) : FragmentPagerAdapter( fm ) {

    companion object{
        const val TOTAL_PAGES = 2
        const val CREDIT_CARDS_PAGE_POS = 0
    }

    /*
     * getItem() é invocado para devolver uma instância do
     * fragmento correspondendo a posição (seção/página)
     * informada.
     * */
    override fun getItem( position: Int )
        = when( position ){
            CREDIT_CARDS_PAGE_POS -> ConfigCreditCardsListFragment()
            else -> ConfigNewCreditCardFragment()
        }

    override fun getPageTitle( position: Int )
        = context.getString(
            when( position ){
                CREDIT_CARDS_PAGE_POS -> ConfigCreditCardsListFragment.TAB_TITLE
                else -> ConfigNewCreditCardFragment.TAB_TITLE
            }
        )

    override fun getCount()
        = TOTAL_PAGES
}