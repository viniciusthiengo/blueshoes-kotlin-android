package thiengo.com.br.blueshoes.view.config


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_config_list.*
import thiengo.com.br.blueshoes.R


abstract class ConfigListFragment : ConfigFormFragment() {

    var callbackMainButtonUpdate : (Boolean)->Unit = {}
    var callbackBlockFields : (Boolean)->Unit = {}
    var callbackRemoveItem : (Boolean)->Unit = {}

    override fun getLayoutResourceID()
        = R.layout.fragment_config_list

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
    fun callbacksToChangeItem(
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
     * Método que inicializa a lista de cartões de crédito.
     * */
    private fun initItems(){
        rv_items.setHasFixedSize( false )

        val layoutManager = LinearLayoutManager( activity )
        rv_items.layoutManager = layoutManager

        val adapter = getRecyclerViewAdapter()
        adapter.registerAdapterDataObserver( RecyclerViewObserver() )
        rv_items.adapter = adapter
    }

    abstract fun getRecyclerViewAdapter() : RecyclerView.Adapter<out RecyclerView.ViewHolder>

    /*
     * Com o RecyclerView.AdapterDataObserver é possível
     * escutar o tamanho atual da lista de itens vinculada
     * ao RecyclerView e caso essa lista esteja vazia, então
     * podemos apresentar uma mensagem ao usuário informando
     * sobre a lista vazia.
     * */
    inner class RecyclerViewObserver :
        RecyclerView.AdapterDataObserver() {

        override fun onItemRangeRemoved(
            positionStart: Int,
            itemCount: Int ) {

            super.onItemRangeRemoved( positionStart, itemCount )

            tv_empty_list.visibility =
                if( rv_items.adapter!!.itemCount == 0 )
                    View.VISIBLE
                else
                    View.GONE
        }
    }
}
