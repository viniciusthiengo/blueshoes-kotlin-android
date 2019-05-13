package thiengo.com.br.blueshoes.data

import android.content.Context
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.NavMenuItem

class NavMenuItemsDataBase( context: Context ) {

    /*
     * Itens de menu gaveta que sempre estarão presentes,
     * independente do status do usuário (conectado ou
     * não).
     * */
    val items = listOf(
        NavMenuItem(
            R.id.item_all_shoes.toLong(),
            context.getString(R.string.item_all_shoes)
        ),
        NavMenuItem(
            R.id.item_flip_flops.toLong(),
            context.getString(R.string.item_flip_flops)
        ),
        NavMenuItem(
            R.id.item_cleats.toLong(),
            context.getString(R.string.item_cleats)
        ),
        NavMenuItem(
            R.id.item_sandals.toLong(),
            context.getString(R.string.item_sandals)
        ),
        NavMenuItem(
            R.id.item_ballet_shoes.toLong(),
            context.getString(R.string.item_ballet_shoes)
        ),
        NavMenuItem(
            R.id.item_suit_shoes.toLong(),
            context.getString(R.string.item_suit_shoes)
        ),
        NavMenuItem(
            R.id.item_shoes.toLong(),
            context.getString(R.string.item_shoes)
        ),
        NavMenuItem(
            R.id.item_performance_shoes.toLong(),
            context.getString(R.string.item_performance_shoes)
        ),
        NavMenuItem(
            R.id.item_contact.toLong(),
            context.getString(R.string.item_contact),
            R.drawable.ic_email_black_24dp
        ),
        NavMenuItem(
            R.id.item_about.toLong(),
            context.getString(R.string.item_about),
            R.drawable.ic_domain_black_24dp
        ),
        NavMenuItem(
            R.id.item_privacy_policy.toLong(),
            context.getString(R.string.item_privacy_policy),
            R.drawable.ic_shield_lock_black_24dp
        )
    )

    /*
     * Itens de menu gaveta que estarão presentes somente
     * quando o usuário estiver conectado.
     * */
    val itemsLogged = listOf(
        NavMenuItem(
            R.id.item_my_orders.toLong(),
            context.getString(R.string.item_my_orders),
            R.drawable.ic_package_variant_closed_black_24dp
        ),
        NavMenuItem(
            R.id.item_settings.toLong(),
            context.getString(R.string.item_settings),
            R.drawable.ic_settings_black_24dp
        ),
        NavMenuItem(
            R.id.item_sign_out.toLong(),
            context.getString(R.string.item_sign_out),
            R.drawable.ic_exit_run_black_24dp
        )
    )

    fun getLastItemId() = items.last().id

    fun getFirstItemLoggedId() = itemsLogged.first().id


    companion object{
        const val SP_NAME = "SP_NAV_MENU"
        const val SP_ITEM_ID_KEY = "item-id"
        const val SP_IS_ACTIVITY_KEY = "is-activity"
    }

    private fun getSP( context: Context )
        = context.getSharedPreferences(
            SP_NAME,
            Context.MODE_PRIVATE
        )

    /*
     * Salva o ID do último item de menu selecionado que
     * aciona um fragmento.
     * */
    fun saveLastSelectedItemFragmentID( context: Context, itemID: Long ){
        val sp = getSP( context )
        sp.edit().putLong( SP_ITEM_ID_KEY, itemID ).apply()
    }

    /*
     * Retorna o ID do último item de menu selecionado que
     * aciona um fragmento.
     * */
    fun getLastSelectedItemFragmentID( context: Context ) : Long {
        val sp = getSP( context )
        return sp.getLong( SP_ITEM_ID_KEY, 0 )
    }

    /*
     * Salva se o último item de menu acionado foi ou não
     * um item que aciona uma atividade.
     * */
    fun saveIsActivityItemFired( context: Context, isActivity: Boolean ){
        val sp = getSP( context )
        sp.edit()
            .putBoolean( SP_IS_ACTIVITY_KEY, isActivity )
            .apply()
    }

    /*
     * Informa se o último item de menu acionado foi ou não
     * um item que aciona uma atividade.
     * */
    fun wasActivityItemFired( context: Context ) : Boolean {
        val sp = getSP( context )
        return sp.getBoolean( SP_IS_ACTIVITY_KEY, false )
    }
}