package thiengo.com.br.blueshoes.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.SystemClock
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.proxy_screen.*
import thiengo.com.br.blueshoes.R


abstract class FormActivity :
    AppCompatActivity(),
    TextView.OnEditorActionListener {


    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_form )
        setSupportActionBar( toolbar )

        /*
         * Para liberar o back button na barra de topo da
         * atividade.
         * */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        /*
         * Hackcode para que a imagem de background do layout não
         * se ajuste de acordo com a abertura do teclado de
         * digitação. Caso utilizando o atributo
         * android:background, o ajuste ocorre, desconfigurando o
         * layout.
         * */
        window.setBackgroundDrawableResource( R.drawable.bg_activity )

        /*
         * Colocando a View de um arquivo XML como View filha
         * do item indicado no terceiro argumento.
         * */
        View.inflate(
            this,
            getLayoutResourceID(),
            fl_form
        )
    }

    abstract fun getLayoutResourceID() : Int

    /*
     * Para permitir que o back button tenha a ação de volta para
     * a atividade anterior.
     * */
    override fun onOptionsItemSelected( item: MenuItem ): Boolean {
        if( item.itemId == android.R.id.home ){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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

    /*
     * Apresenta a tela de bloqueio que diz ao usuário que
     * algo está sendo processado em background e que ele
     * deve aguardar.
     * */
    protected fun showProxy( status: Boolean ){
        fl_proxy_container.visibility =
            if( status )
                View.VISIBLE
            else
                View.GONE
    }

    /*
     * Método responsável por apresentar um SnackBar com as
     * corretas configurações de acordo com o feedback do
     * back-end Web.
     * */
    protected fun snackBarFeedback(
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
         * Criando o objeto Drawable que entrará como ícone
         * inicial no texto do SnackBar.
         * */
        val iconResource =
            if( status )
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

        val iconColor =
            if( status )
                ContextCompat
                    .getColor(
                        this,
                        R.color.colorNavButton
                    )
            else
                Color.RED
        img.setColorFilter(
            iconColor,
            PorterDuff.Mode.SRC_ATOP
        )

        /*
         * Acessando o TextView padrão do SnackBar para assim
         * colocarmos um ícone nele via objeto Spannable.
         * */
        val textView = snackBar.view.findViewById(
            com.google.android.material.R.id.snackbar_text
        ) as TextView

        /*
         * O espaçamento aplicado como parte do argumento
         * de SpannableString() é para que haja um espaço
         * entre o ícone e o texto do SnackBar, como
         * informado em protótipo estático.
         * */
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

    /*
     * Método template.
     * Responsável por conter o algoritmo de envio / validação
     * de dados. Algoritmo vinculado ao menos ao principal
     * botão em tela.
     * */
    fun mainAction( view: View? = null ){
        blockFields( true )
        isMainButtonSending( true )
        showProxy( true )
        backEndFakeDelay()
    }

    /*
     * Método único.
     * */
    abstract fun backEndFakeDelay() : Unit

    /*
     * Necessário para que os campos de formulário não possam
     * ser acionados depois de enviados os dados.
     * */
    abstract fun blockFields( status: Boolean )

    /*
     * Muda o rótulo do botão principal de acordo com o status
     * do envio de dados.
     * */
    abstract fun isMainButtonSending(status: Boolean )


    /*
     * Fake method - Somente para testes temporários em atividades
     * e fragmentos que contêm formulários.
     * */
    protected fun backEndFakeDelay(
        statusAction: Boolean,
        feedbackMessage: String
    ){

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
                        statusAction,
                        feedbackMessage
                    )
                }
            }
        }.start()
    }
}
