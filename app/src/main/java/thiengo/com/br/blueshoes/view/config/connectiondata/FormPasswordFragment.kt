package thiengo.com.br.blueshoes.view.config.connectiondata


import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_config_password.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.util.isValidPassword
import thiengo.com.br.blueshoes.util.validate
import thiengo.com.br.blueshoes.view.FormFragment
import thiengo.com.br.blueshoes.view.config.ConfigFormFragment


class FormPasswordFragment :
    ConfigFormFragment() {

    override fun title()
        = R.string.config_connection_data_tab_password

    override fun getLayoutResourceID()
        = R.layout.fragment_config_password

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        bt_update_password.setOnClickListener{
            callPasswordDialog()
        }

        et_new_password.validate(
            { it.isValidPassword() },
            getString( R.string.invalid_password )
        )

        et_new_password_confirm.validate(
            {
                /*
                 * O toString() em et_new_email.text.toString() é
                 * necessário, caso contrário a validação falha
                 * mesmo quando é para ser ok.
                 * */
                (et_new_password.text.isNotEmpty()
                    && it.equals( et_new_password.text.toString() ))
                    || et_new_password.text.isEmpty()
            },
            getString( R.string.invalid_confirmed_password )
        )

        et_new_password_confirm.setOnEditorActionListener( this )
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_password )
        )
    }

    override fun blockFields( status: Boolean ){
        et_new_password.isEnabled = !status
        et_new_password_confirm.isEnabled = !status
        bt_update_password.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_update_password.text =
            if( status )
                getString( R.string.update_password_going )
            else
                getString( R.string.update_password )
    }
}
