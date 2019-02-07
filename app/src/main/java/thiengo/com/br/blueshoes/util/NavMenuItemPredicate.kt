package thiengo.com.br.blueshoes.util

import androidx.recyclerview.selection.SelectionTracker
import thiengo.com.br.blueshoes.data.NavMenuItemsDataBase
import thiengo.com.br.blueshoes.view.MainActivity


/*
 * SelectionTracker.SelectionPredicate é utilizada para definir
 * quais itens poderão ser selecionados e quantos deles.
 *
 * A parametrização deve ser do tipo da chave estável definida
 * em ItemKeyProvider.
 * */
class NavMenuItemPredicate( val activity: MainActivity ) :
    SelectionTracker.SelectionPredicate<Long>() {

    /*
     * Retorne true se puder ter múltipla seleção e false para
     * somente uma seleção.
     * */
    override fun canSelectMultiple() = false

    /*
     * Retorne true se o item referente a key puder ser definido
     * como nextState.
     * */
    override fun canSetStateForKey(
        key: Long,
        nextState: Boolean ) : Boolean{

        /*
         * A lógica de negócio abaixo foi adotada para que não
         * seja possível deixar o menu gaveta com nenhum item
         * selecionado. Assim, se o status do item acionado for
         * false (nextState = false) e se ele for o item já
         * selecionado, então retornamos false para que o status
         * dele não mude, continue como "selecionado".
         * */
        if( !nextState ){
            val lastItemId = NavMenuItemsDataBase( activity ).getLastItemId()
            val firstItemLoggedId = NavMenuItemsDataBase( activity ).getFirstItemLoggedId()

            val sizeNavMenuItems = activity.selectNavMenuItems.selection.size()
            val sizeNavMenuItemsLogged = activity.selectNavMenuItemsLogged.selection.size()

            /*
             * Há somente duas situações onde um item pode
             * acionar canSetStateForKey() com nextState sendo
             * false:
             *
             *      1ª - Quando o item está selecionado e então
             *      ele é acionado novamente, para que perca a
             *      seleção;
             *
             *      2ª - Quando é removida a seleção do item
             *      via deselect(), como estamos fazendo na
             *      atividade principal de projeto.
             *
             * No caso da 2ª situação, isso acontece porque
             * temos dois objetos de seleção sendo utilizados,
             * sendo assim, é preciso saber o intervalo do ID
             * do item alvo, pois ele somente não perde a seleção
             * se ele mesmo receber um novo acionamento. Em caso
             * de item de outra lista, ele deve sim perder a
             * seleção.
             * */
            if( (key <= lastItemId && sizeNavMenuItemsLogged == 0)
                || (key >= firstItemLoggedId && sizeNavMenuItems == 0) ){
                return false
            }
        }

        return true
    }

    /*
     * Retorne true se o item referente a position puder ser definido
     * como nextState.
     * */
    override fun canSetStateAtPosition(
        position: Int,
        nextState: Boolean ) = true
}