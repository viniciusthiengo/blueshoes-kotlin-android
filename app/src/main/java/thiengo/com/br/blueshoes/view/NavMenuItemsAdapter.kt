package thiengo.com.br.blueshoes.view

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.SelectionTracker
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.NavMenuItem
import thiengo.com.br.blueshoes.util.NavMenuItemDetails

class NavMenuItemsAdapter( val menuItems: List<NavMenuItem> ) :
    RecyclerView.Adapter<NavMenuItemsAdapter.ViewHolder>() {

    lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int ): ViewHolder {

        val layout = LayoutInflater
            .from( parent.context )
            .inflate(
                R.layout.nav_menu_item,
                parent,
                false
            )

        return ViewHolder( layout )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int ) {

        holder.setData( menuItems[ position ] )
    }

    override fun getItemCount() = menuItems.size

    inner class ViewHolder( itemView: View ) :
        RecyclerView.ViewHolder( itemView ){

        private val ivIcon : ImageView
        private val tvLabel : TextView

        val itemDetails: NavMenuItemDetails

        init{
            ivIcon = itemView.findViewById( R.id.iv_icon )
            tvLabel = itemView.findViewById( R.id.tv_label )

            itemDetails = NavMenuItemDetails()
        }

        fun setData( item: NavMenuItem ){

            tvLabel.text = item.label

            if( item.iconId != NavMenuItem.DEFAULT_ID ){
                ivIcon.setImageResource( item.iconId )
                ivIcon.visibility = View.VISIBLE
            }
            else{
                ivIcon.visibility = View.GONE
            }

            /*
             * São nos blocos condicionais a seguir que devem vir os
             * algoritmos de atualização de UI, isso para indicar o
             * item selecionado e os itens não selecionados. Seguindo
             * as dicas da comunidade, sempre utilize o isActivated
             * no View container de item.
             * */
            itemDetails.item = item
            itemDetails.adapterPosition = adapterPosition

            if( selectionTracker.isSelected( itemDetails.getSelectionKey() ) ){
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorNavItemSelected
                    )
                )
            }
            else{
                itemView.setBackgroundColor( Color.TRANSPARENT )
            }
        }
    }
}