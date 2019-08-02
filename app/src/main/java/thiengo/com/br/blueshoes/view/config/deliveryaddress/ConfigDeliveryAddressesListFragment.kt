package thiengo.com.br.blueshoes.view.config.deliveryaddress


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_config_delivery_addresses_list.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.data.DeliveryAddressesDataBase
import thiengo.com.br.blueshoes.view.FormFragment


class ConfigDeliveryAddressesListFragment :
    FormFragment() {

    companion object{
        const val TAB_TITLE = R.string.config_delivery_addresses_tab_list
    }

    private var callbackMainButtonUpdate : (Boolean)->Unit = {}
    private var callbackBlockFields : (Boolean)->Unit = {}
    private var callbackRemoveItem : (Boolean)->Unit = {}


    override fun getLayoutResourceID()
        = R.layout.fragment_config_delivery_addresses_list

    override fun backEndFakeDelay() {
        backEndFakeDelay(
            true,
            getString( R.string.delivery_address_removed )
        )
    }

    override fun blockFields(status: Boolean) {
        callbackBlockFields( status )
    }

    override fun isMainButtonSending( status: Boolean ) {
        callbackMainButtonUpdate( status )
        callbackRemoveItem( status )
    }

    /*
     * Método utilizado para receber os callbacks do adapter
     * do RecyclerView para assim poder atualizar os itens
     * de adapter.
     * */
    fun callbacksToRemoveItem(
        mainButtonUpdate: (Boolean)->Unit,
        blockFields: (Boolean)->Unit,
        removeItem: (Boolean)->Unit ){

        callbackMainButtonUpdate = mainButtonUpdate
        callbackBlockFields = blockFields
        callbackRemoveItem = removeItem
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        updateFlFormToFullFreeScreen()
        initItems()
    }

    /*
     * Método que inicializa a lista de endereços de entrega.
     * */
    private fun initItems(){
        rv_delivery_addresses.setHasFixedSize( false )

        val layoutManager = LinearLayoutManager( activity )
        rv_delivery_addresses.layoutManager = layoutManager

        val adapter = ConfigDeliveryAddressesListItemsAdapter(
            this,
            DeliveryAddressesDataBase.getItems()
        )
        adapter.registerAdapterDataObserver( RecyclerViewObserver() )
        rv_delivery_addresses.adapter = adapter
    }

    /*
     * Com o RecyclerView.AdapterDataObserver é possível
     * escutar o tamanho atual da lista de itens vinculada
     * ao RecyclerView e caso essa lista esteja vazia, então
     * podemos apresentar uma mensagem ao usuário informando
     * sobre a lista vazia.
     * */
    inner class RecyclerViewObserver :
        RecyclerView.AdapterDataObserver() {

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)

            tv_empty_list.visibility =
                if( rv_delivery_addresses.adapter!!.itemCount == 0 )
                    View.VISIBLE
                else
                    View.GONE
        }
    }
}
