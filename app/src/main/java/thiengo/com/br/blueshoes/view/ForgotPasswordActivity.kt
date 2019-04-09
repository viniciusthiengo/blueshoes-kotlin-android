package thiengo.com.br.blueshoes.view

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.content_form.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.util.isValidEmail
import thiengo.com.br.blueshoes.util.validate


class ForgotPasswordActivity :
    FormActivity(),
    TextView.OnEditorActionListener {

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
    }

    /*
     * Caso o usuário toque no botão "Done" do teclado virtual
     * ao invés de tocar no botão "Entrar". Mesmo assim temos
     * de processar o formulário.
     * */
    override fun onEditorAction(
        view: TextView,
        actionId: Int,
        event: KeyEvent? ): Boolean {

        mainAction()
        return false
    }

    override fun mainAction( view: View? ){
        blockFields( true )
        isMainButtonSending( true )
        showProxy( true )
        backEndFakeDelay()
    }

    override fun blockFields( status: Boolean ){
        et_email.isEnabled = !status
        bt_recover_password.isEnabled = !status
    }

    override fun isMainButtonSending(status: Boolean ){
        bt_recover_password.text =
            if( status )
                getString( R.string.recover_password_going )
            else
                getString( R.string.recover_password )
    }

    private fun backEndFakeDelay(){
        Thread{
            kotlin.run {
                /*
                 * Simulando um delay de latência de
                 * 1 segundo.
                 * */
                SystemClock.sleep( 1000 )

                runOnUiThread {
                    blockFields( false )
                    isMainButtonSending( false )
                    showProxy( false )

                    snackBarFeedback(
                        fl_form_container,
                        false,
                        getString( R.string.invalid_login )
                    )
                }
            }
        }.start()
    }
}
