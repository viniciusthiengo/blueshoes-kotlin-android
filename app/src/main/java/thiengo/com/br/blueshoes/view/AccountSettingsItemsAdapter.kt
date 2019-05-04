package thiengo.com.br.blueshoes.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.AccountSettingItem

class AccountSettingsItemsAdapter(
        private val items: List<AccountSettingItem>
    ) :
    RecyclerView.Adapter<AccountSettingsItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        type: Int ): ViewHolder {

        val layout = LayoutInflater
            .from( parent.context )
            .inflate(
                R.layout.account_settings_item,
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
        RecyclerView.ViewHolder( itemView ){

        private val tvLabel : TextView
        private val tvDescription : TextView

        init{
            tvLabel = itemView.findViewById( R.id.tv_label )
            tvDescription = itemView.findViewById( R.id.tv_description )
        }

        fun setData( item: AccountSettingItem ){
            tvLabel.text = item.label
            tvDescription.text = item.description
        }
    }
}