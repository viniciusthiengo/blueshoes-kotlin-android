package thiengo.com.br.blueshoes.util

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import thiengo.com.br.blueshoes.domain.NavMenuItem

/*
 * Uma implementação de ItemDetails fornece à biblioteca de seleção
 * acesso a informações sobre um específico item do RecyclerView. Esta
 * classe é um componente chave no controle dos comportamentos da
 * biblioteca de seleção no contexto de uma atividade específica.
 * */
class NavMenuItemDetails(
    var item: NavMenuItem? = null,
    var adapterPosition: Int = -1
) : ItemDetailsLookup.ItemDetails<Long>() {

    /*
     * Retorna a posição do adaptador do item
     * (ViewHolder.adapterPosition).
     * */
    override fun getPosition() = adapterPosition

    /*
     * Retorna a entidade que é a chave de seleção do item.
     * */
    override fun getSelectionKey() = item!!.id

    /*
     * Retorne "true" se o item tiver uma chave de seleção. Se true
     * não for retornado o item em foco (acionado pelo usuário) não
     * será selecionado.
     * */
    override fun inSelectionHotspot( e: MotionEvent ) = true
}