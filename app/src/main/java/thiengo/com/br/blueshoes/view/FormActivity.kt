package thiengo.com.br.blueshoes.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.proxy_screen.*
import thiengo.com.br.blueshoes.R


abstract class FormActivity : AppCompatActivity() {


    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_form )
        setSupportActionBar( toolbar )

        //supportActionBar?.setDisplayHomeAsUpEnabled( true )

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
    }

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
            android.support.design.R.id.snackbar_text
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
     * Responsável por conter o algoritmo de envio / validação
     * de dados. Algoritmo vinculado ao menos ao principal
     * botão em tela.
     * */
    abstract fun mainAction( view: View? = null )

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
}
