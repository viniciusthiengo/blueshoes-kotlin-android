package thiengo.com.br.blueshoes.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import thiengo.com.br.blueshoes.R

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.text.style.ImageSpan
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.Spanned
import android.util.Patterns
import android.view.ViewGroup
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*


class LoginActivity :
    AppCompatActivity(),
    TextView.OnEditorActionListener,
    KeyboardUtils.OnSoftInputChangedListener {


    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_login )
        setSupportActionBar( toolbar )

        supportActionBar?.setDisplayHomeAsUpEnabled( true )

        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */
        window.setBackgroundDrawableResource( R.drawable.bg_activity )

        /*
         * Com a API KeyboardUtils conseguimos de maneira
         * simples obter o status atual do teclado virtual (aberto /
         * fechado) e assim prosseguir com algoritmos de ajuste de
         * layout.
         * */
        KeyboardUtils.registerSoftInputChangedListener( this, this )

        /*
         * Colocando configuração de validação de campo de email
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_email.addTextChangedListener( object: TextWatcher{
            override fun afterTextChanged( content: Editable ) {

                val message = getString(R.string.invalid_email)

                et_email.error =
                    if( content.isNotEmpty()
                        && Patterns.EMAIL_ADDRESS.matcher(content).matches() )
                        null
                    else
                        message
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int ) {}

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int) {}
        } )

        /*
         * Colocando configuração de validação de campo de senha
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_password.addTextChangedListener( object: TextWatcher{
            override fun afterTextChanged( content: Editable ) {

                val message = getString(R.string.invalid_password)

                et_password.error =
                    if( content.length > 5 )
                        null
                    else
                        message
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int ) {}

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int) {}
        } )

        et_password.setOnEditorActionListener( this )
    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
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

        if( actionId == EditorInfo.IME_ACTION_DONE ){
            closeVirtualKeyBoard( view )
            login()
            return true
        }
        return false
    }

    private fun closeVirtualKeyBoard( view: View ){
        val imm = getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        imm.hideSoftInputFromWindow( view.windowToken, 0 )
    }

    fun login( view: View? = null ){
        blockFields( true )
        isSignInGoing( true )
        showProxy( true )
        backEndFakeDelay()
    }

    /*
     * Necessário para que os campos de formulário não possam
     * ser acionados depois de enviados os dados.
     * */
    private fun blockFields( status: Boolean ){
        et_email.isEnabled = !status
        et_password.isEnabled = !status
        bt_login.isEnabled = !status
    }

    /*
     * Muda o rótulo do botão de login de acordo com o status
     * do envio de dados de login.
     * */
    private fun isSignInGoing( status: Boolean ){
        bt_login.text = if( status )
            getString(R.string.sign_in_going)
        else
            getString(R.string.sign_in)
    }

    /*
     * Apresenta a tela de bloqueio que diz ao usuário que
     * algo está sendo processado em background e que ele
     * deve aguardar.
     * */
    private fun showProxy( status: Boolean ){
        fl_proxy_container.visibility = if( status )
                View.VISIBLE
            else
                View.GONE
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
                    isSignInGoing( false )
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

    /*
     * Método responsável por apresentar um SnackBar com as
     * corretas configurações de acordo com o feedback do
     * back-end Web.
     * */
    private fun snackBarFeedback(
        viewContainer: ViewGroup,
        status: Boolean,
        message: String ){

        val snackBar = Snackbar
            .make(
                viewContainer,
                message,
                Snackbar.LENGTH_LONG
            )

        /*
         * Acessando o TextView padrão do SnackBar para assim
         * colocarmos um ícone nele via objeto Spannable.
         * */
        val snackBarView = snackBar.view
        val textView = snackBarView.findViewById(
            android.support.design.R.id.snackbar_text
        ) as TextView


        /*
         * Criando o objeto Drawable que entrará como ícone
         * inicial no texto do SnackBar.
         * */
        val iconResource = if( status )
            R.drawable.ic_check_black_18dp
        else
            R.drawable.ic_close_black_18dp

        val img = ResourcesCompat
            .getDrawable(
                resources,
                iconResource,
                null
            )
        img!!.setBounds(
            0,
            0,
            img.intrinsicWidth,
            img.intrinsicHeight
        )

        val iconColor = if( status )
                ContextCompat
                    .getColor(
                        this,
                        R.color.colorNavButton
                    )
            else
                Color.RED
        img.setColorFilter( iconColor, PorterDuff.Mode.SRC_ATOP )

        val spannedText = SpannableString( "     ${textView.text}" )
        spannedText.setSpan(
            ImageSpan( img, ImageSpan.ALIGN_BOTTOM ),
            0,
            1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.setText( spannedText, TextView.BufferType.SPANNABLE )

        snackBar.show()
    }


    override fun onSoftInputChanged( height: Int ) {

        if( ScreenUtils.isPortrait() ){
            changePrivacyPolicyConstraints(
                KeyboardUtils.isSoftInputVisible( this )
            )
        }
    }

    private fun changePrivacyPolicyConstraints(
            isKeyBoardOpened: Boolean
        ){

        val privacyId = tv_privacy_policy.id
        val parent = tv_privacy_policy.parent as ConstraintLayout
        val constraintSet = ConstraintSet()

        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(
            privacyId,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        constraintSet.constrainHeight(
            privacyId,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        /*
         * Centralizando a View horizontalmente no
         * ConstraintLayout.
         * */
        constraintSet.centerHorizontally(
            privacyId,
            ConstraintLayout.LayoutParams.PARENT_ID
        )

        if( isKeyBoardOpened ){
            /*
             * Se o teclado virtual estiver aberto, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada a
             * View acima dela (tv_sign_up).
             * */
            constraintSet.connect(
                privacyId,
                ConstraintLayout.LayoutParams.TOP,
                tv_sign_up.id,
                ConstraintLayout.LayoutParams.BOTTOM,
                (12 * ScreenUtils.getScreenDensity()).toInt()
            )
        }
        else{
            /*
             * Se o teclado virtual estiver fechado, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada ao
             * fundo do ConstraintLayout ancestral.
             * */
            constraintSet.connect(
                privacyId,
                ConstraintLayout.LayoutParams.BOTTOM,
                ConstraintLayout.LayoutParams.PARENT_ID,
                ConstraintLayout.LayoutParams.BOTTOM
            )
        }

        constraintSet.applyTo( parent )
    }
}
