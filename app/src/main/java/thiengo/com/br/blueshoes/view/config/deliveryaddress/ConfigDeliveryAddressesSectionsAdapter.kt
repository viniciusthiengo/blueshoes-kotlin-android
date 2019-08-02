package thiengo.com.br.blueshoes.view.config.deliveryaddress

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
class ConfigDeliveryAddressesSectionsAdapter(
    val context: Context,
    fm: FragmentManager ) : FragmentPagerAdapter( fm ) {

    companion object{
        const val TOTAL_PAGES = 2
        const val HOST_DELIVERY_ADDRESSES_PAGE_POS = 0
    }

    /*
     * getItem() é invocado para devolver uma instância do
     * fragmento correspondendo a posição (seção/página)
     * informada.
     * */
    override fun getItem( position: Int )
        = when( position ){
            HOST_DELIVERY_ADDRESSES_PAGE_POS ->
                ConfigDeliveryAddressHostFragment()
            else ->
                ConfigNewDeliveryAddressFragment()
        }

    override fun getPageTitle( position: Int )
        = context.getString(
            when( position ){
                HOST_DELIVERY_ADDRESSES_PAGE_POS ->
                    ConfigDeliveryAddressesListFragment.TAB_TITLE
                else ->
                    ConfigNewDeliveryAddressFragment.TAB_TITLE
            }
        )

    override fun getCount()
        = TOTAL_PAGES
}