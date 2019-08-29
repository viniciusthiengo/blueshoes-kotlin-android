package thiengo.com.br.blueshoes.view.config.deliveryaddress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.DeliveryAddress
import thiengo.com.br.blueshoes.view.config.ConfigListFragment


class DeliveryAddressesListAdapter(
        private val fragment : ConfigListFragment,
        private val items: MutableList<DeliveryAddress>
    ) :
    RecyclerView.Adapter<DeliveryAddressesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int ): ViewHolder {

        val layout = LayoutInflater
            .from( parent.context )
            .inflate(
                R.layout.delivery_address_item,
                parent,
                false
            )

        return ViewHolder( layout )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int ) {

        holder.setData( items[ position ] )
    }

    override fun getItemCount() = items.size

    inner class ViewHolder( itemView: View ) :
        RecyclerView.ViewHolder( itemView ),
        View.OnClickListener {

        private val tvStreet : TextView
        private val tvNumber : TextView
        private val tvZipCode : TextView
        private val tvNeighborhood : TextView
        private val tvCity : TextView
        private val tvState : TextView
        private val tvComplement : TextView
        private val btUpdate : Button
        private val btRemove : Button

        init{
            tvStreet = itemView.findViewById( R.id.tv_street )
            tvNumber = itemView.findViewById( R.id.tv_number )
            tvZipCode = itemView.findViewById( R.id.tv_zip_code )
            tvNeighborhood = itemView.findViewById( R.id.tv_neighborhood )
            tvCity = itemView.findViewById( R.id.tv_city )
            tvState = itemView.findViewById( R.id.tv_state )
            tvComplement = itemView.findViewById( R.id.tv_complement )

            btUpdate = itemView.findViewById( R.id.bt_update )
            btUpdate.setOnClickListener( this )

            btRemove = itemView.findViewById( R.id.bt_remove )
            btRemove.setOnClickListener( this )
        }

        fun setData( item: DeliveryAddress ){

            tvStreet.setText( item.street )
            tvNumber.setText( item.number.toString() )
            tvZipCode.setText( item.zipCode )
            tvNeighborhood.setText( item.neighborhood )
            tvCity.setText( item.city )
            tvState.setText( item.getStateName( fragment.context!! ) )
            tvComplement.setText( item.complement )
        }

        override fun onClick( view: View ) {
            /*
             * É preciso salvar em uma nova propriedade a posição do
             * item selecionado, pois o valor de adapterPosition está
             * sempre sendo atualizado e isso, o acesso a adapterPosition
             * diretamente dentro do callback, poderia ocosionar em
             * uma Exception.
             * */
            val selectedItem = adapterPosition

            if( view.id == btRemove.id ){
                toRemove( selectedItem )
            }
            else{
                toUpdate( selectedItem )
            }
        }

        private fun toRemove( position: Int ){

            fragment.callbacksToChangeItem(
                {
                    status ->
                        btRemove.text =
                            if( status )
                                fragment.getString( R.string.remove_item_going )
                            else
                                fragment.getString( R.string.remove_item )
                },
                {
                    status ->
                        btUpdate.isEnabled = !status
                        btRemove.isEnabled = !status
                },
                {
                    status ->
                        if( !status ){
                            items.removeAt( position )
                            notifyItemRemoved( position )
                        }
                }
            )

            fragment.callPasswordDialog()
        }

        private fun toUpdate( position: Int ){

            val updateFrag = FormUpdateDeliveryAddressFragment()

            /*
             * Colocando como dado de transição o item selecionado para
             * atualização.
             * */
            val bundle = Bundle()
            bundle.putParcelable(
                DeliveryAddress.KEY,
                items[ position ]
            )
            updateFrag.arguments = bundle

            val transaction = fragment
                .fragmentManager!!
                .beginTransaction()

            /*
             * O acesso ao FrameLayout root volta a ocorrer para que
             * seja possível o replace de fragmentos dentro da mesma
             * janela de ViewPager.
             * */
            transaction
                .replace(
                    R.id.fl_root,
                    updateFrag
                )

            /*
             * Com o setTransition() e addToBackStack() nós estamos,
             * respectivamente permitindo uma transição entre fragmentos
             * e os colocando em uma pilha de fragmentos para que seja
             * possível voltar ao fragmento anteriormente apresentado
             * na mesma janela de ViewPager.
             * */
            transaction
                .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                .addToBackStack( null )
                .commit()
        }
    }
}