package thiengo.com.br.blueshoes.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.info_block.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.util.isValidEmail
import thiengo.com.br.blueshoes.util.validate


class ForgotPasswordActivity :
    FormActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        /*
         * Colocando a View de um arquivo XML como View filha
         * do item indicado no terceiro argumento.
         * */
        View.inflate(
            this,
            R.layout.content_forgot_password,
            fl_form
        )

        /*
         * Colocando configuração de validação de campo de email
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_email.validate(
            {
                it.isValidEmail()
            },
            getString( R.string.invalid_email )
        )

        et_email.setOnEditorActionListener( this )


        tv_info_block.text = getString( R.string.forgot_password_info )
    }

    override fun mainAction( view: View? ){
        blockFields( true )
        isMainButtonSending( true )
        showProxy( true )

        backEndFakeDelay(
            false,
            getString( R.string.invalid_login_email )
        )
    }

    override fun blockFields( status: Boolean ){
        et_email.isEnabled = !status
        bt_recover_password.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_recover_password.text =
            if( status )
                getString( R.string.recover_password_going )
            else
                getString( R.string.recover_password )
    }
}