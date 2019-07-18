package thiengo.com.br.blueshoes.view.config.creditcard


import android.os.Bundle
import android.view.View
import com.santalu.maskedittext.MaskEditText
import kotlinx.android.synthetic.main.fragment_config_new_credit_card.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.view.FormFragment


class ConfigNewCreditCardFragment :
    FormFragment(),
    View.OnFocusChangeListener{

    companion object{
        const val TAB_TITLE = R.string.config_credit_cards_tab_new
    }

    override fun getLayoutResourceID()
        = R.layout.fragment_config_new_credit_card

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        updateFlFormToFullFreeScreen()

        bt_add_credit_card.setOnClickListener{
            callPasswordDialog()
        }

        et_credit_card_security_code.setOnEditorActionListener( this )

        met_credit_card_owner_reg.setOnFocusChangeListener( this )

        //et_credit_card_owner_reg.setText("12033441717")
        met_credit_card_number.setText("4794795248635556")
        et_credit_card_owner_name.setText("Amya Allen")
        met_credit_card_owner_reg.setText("63597340016")
        et_credit_card_expiry_year.setText("2023")
        et_credit_card_security_code.setText("266")
    }

    override fun onFocusChange(
        view: View,
        hasFocus: Boolean) {

        var mask = "" /* Sem máscara. */
        val editText = view as MaskEditText

        /*
         * Garantindo que o conteúdo em teste terá apenas
         * números.
         * */
        val pattern = Regex("[^0-9 ]")
        val content = editText
            .text
            .toString()
            .replace( pattern, "" )

        if( !hasFocus ){
            if( content.length == 11 ){
                /* Máscara CPF. */
                mask = "###.###.###-##"
            }
            else if( content.length == 14 ){
                /* Máscara CNPJ. */
                mask = "##.###.###/####-##"
            }
        }

        /*
         * Aplicando a nova máscara de acordo com a
         * quantidade de números presentes em campo.
         * */
        editText.mask = mask

        /*
         * Para que a nova máscara seja aplicada é preciso
         * forçar uma atualização no campo.
         * */
        editText.setText( content )
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_credit_card )
        )
    }

    override fun blockFields( status: Boolean ){
        met_credit_card_number.isEnabled = !status
        sp_credit_card_enterprise.isEnabled = !status
        et_credit_card_owner_name.isEnabled = !status
        met_credit_card_owner_reg.isEnabled = !status
        sp_credit_card_expiry_month.isEnabled = !status
        et_credit_card_expiry_year.isEnabled = !status
        et_credit_card_security_code.isEnabled = !status
        bt_add_credit_card.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_add_credit_card.text =
            if( status )
                getString( R.string.add_credit_card_going )
            else
                getString( R.string.add_credit_card )
    }
}
