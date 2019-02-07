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
            R.id.item_01.toLong(),
            context.getString(R.string.item_all_shoes)
        ),
        NavMenuItem(
            R.id.item_02.toLong(),
            context.getString(R.string.item_flip_flops)
        ),
        NavMenuItem(
            R.id.item_03.toLong(),
            context.getString(R.string.item_cleats)
        ),
        NavMenuItem(
            R.id.item_04.toLong(),
            context.getString(R.string.item_sandals)
        ),
        NavMenuItem(
            R.id.item_05.toLong(),
            context.getString(R.string.item_ballet_shoes)
        ),
        NavMenuItem(
            R.id.item_06.toLong(),
            context.getString(R.string.item_suit_shoes)
        ),
        NavMenuItem(
            R.id.item_07.toLong(),
            context.getString(R.string.item_shoes)
        ),
        NavMenuItem(
            R.id.item_08.toLong(),
            context.getString(R.string.item_performance_shoes)
        ),
        NavMenuItem(
            R.id.item_09.toLong(),
            context.getString(R.string.item_contact),
            R.drawable.ic_email_black_24dp
        ),
        NavMenuItem(
            R.id.item_10.toLong(),
            context.getString(R.string.item_about),
            R.drawable.ic_domain_black_24dp
        ),
        NavMenuItem(
            R.id.item_11.toLong(),
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
            R.id.item_12.toLong(),
            context.getString(R.string.item_my_orders),
            R.drawable.ic_package_variant_closed_black_24dp
        ),
        NavMenuItem(
            R.id.item_13.toLong(),
            context.getString(R.string.item_settings),
            R.drawable.ic_settings_black_24dp
        ),
        NavMenuItem(
            R.id.item_14.toLong(),
            context.getString(R.string.item_sign_out),
            R.drawable.ic_exit_run_black_24dp
        )
    )

    fun getLastItemId() = items.last().id

    fun getFirstItemLoggedId() = itemsLogged.first().id
}