package thiengo.com.br.blueshoes.view.config

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.AccountSettingItem
import thiengo.com.br.blueshoes.domain.User

class AccountSettingsListAdapter(
        private val items: List<AccountSettingItem>
    ) :
    RecyclerView.Adapter<AccountSettingsListAdapter.ViewHolder>() {

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
        RecyclerView.ViewHolder( itemView ),
        View.OnClickListener {

        private val tvLabel : TextView
        private val tvDescription : TextView

        init{
            itemView.setOnClickListener( this )

            tvLabel = itemView.findViewById( R.id.tv_label )
            tvDescription = itemView.findViewById( R.id.tv_description )
        }

        fun setData( item: AccountSettingItem ){
            tvLabel.text = item.label
            tvDescription.text = item.description
        }

        override fun onClick( view: View ) {
            val activity = view.context as AccountSettingsActivity
            val user = activity.getUser()
            val intent = Intent(
                activity,
                items[ adapterPosition ].activityClass
            )

            intent.putExtra( User.KEY, user )
            activity.startActivity( intent )
        }
    }
}