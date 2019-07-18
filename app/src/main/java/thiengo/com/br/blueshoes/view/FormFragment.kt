package thiengo.com.br.blueshoes.view


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.SystemClock
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.proxy_screen.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.util.isValidPassword
import thiengo.com.br.blueshoes.util.validate


abstract class FormFragment :
    Fragment(),
    TextView.OnEditorActionListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        val viewContainer = inflater
            .inflate(
                R.layout.fragment_form,
                container,
                false
            ) as ViewGroup

        /*
         * Colocando a View de um arquivo XML como View filha
         * do item indicado no terceiro argumento.
         * */
        View.inflate(
            activity,
            getLayoutResourceID(),
            viewContainer.findViewById(R.id.fl_form)
        )

        return viewContainer
    }

    abstract fun getLayoutResourceID() : Int

    /*
     * Caso o usuário toque no botão "Done" do teclado virtual
     * ao invés de tocar no botão "Entrar". Mesmo assim temos
     * de processar o formulário.
     * */
    override fun onEditorAction(
        v: TextView?,
        actionId: Int,
        event: KeyEvent? ): Boolean {

        callPasswordDialog()
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
                        activity!!,
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
            run {
                /*
                 * Simulando um delay de latência de
                 * 1 segundo.
                 * */
                SystemClock.sleep( 1000 )

                activity!!.runOnUiThread {
                    blockFields( false )
                    isMainButtonSending( false )
                    showProxy( false )

                    val containerForm = fl_proxy_container.parent as ViewGroup

                    snackBarFeedback(
                        containerForm,
                        statusAction,
                        feedbackMessage
                    )
                }
            }
        }.start()
    }

    /*
     * Método responsável por invocar o Dialog de password antes
     * que o envio do formulário ocorra. Dialog necessário em
     * alguns formulários críticos onde parte da validação é a
     * verificação da senha.
     * */
    fun callPasswordDialog(){

        val builder = AlertDialog.Builder( activity!! )
        val inflater = activity!!.layoutInflater

        /*
         * Inflando o layout e configurando o AlertDialog. O
         * valor null está sendo colocado como segundo argumento
         * de inflate(), pois o layout parent do layout que
         * está sendo inflado será o layout nativo do dialog.
         * */
        builder
            .setView( inflater.inflate(R.layout.dialog_password, null) )
            .setPositiveButton(
                R.string.dialog_password_go,
                { dialog, id -> mainAction() }
            )
            .setNegativeButton(
                R.string.dialog_password_cancel,
                { dialog, id -> dialog.cancel() }
            )
            .setCancelable( false )

        val dialog = builder.create()
        dialog.setOnShowListener(
            object : DialogInterface.OnShowListener{

                override fun onShow( d: DialogInterface? ) {
                    /*
                     * É preciso colocar qualquer configuração
                     * extra das Views do Dialog dentro do
                     * listener de "dialog em apresentação",
                     * caso contrário uma NullPointerException
                     * será gerada, tendo em mente que é somente
                     * quando o "dialog está em apresentação"
                     * que as Views dele existem como objetos.
                     * */

                    dialog
                        .getButton( AlertDialog.BUTTON_POSITIVE )
                        .setTextColor( ColorUtils.getColor(R.color.colorText) )

                    dialog
                        .getButton( AlertDialog.BUTTON_NEGATIVE )
                        .setTextColor( ColorUtils.getColor(R.color.colorText) )

                    val etPassword = dialog.findViewById<EditText>(R.id.et_password)!!
                    etPassword.validate(
                        { it.isValidPassword() },
                        getString( R.string.invalid_password )
                    )
                    etPassword.setOnEditorActionListener{
                        view, actionId, event ->
                            dialog.cancel()
                            mainAction()
                            false
                    }
                }
            }
        )
        dialog.show()
    }

    /*
     * Método necessário para atualizar o ViewGroup
     * fl_form, que é container dos layouts de formulários
     * carregados em fragment_form, deixando ele
     * pronto para receber uma lista de itens ou formulários
     * que têm os próprios padding e posicionamento.
     * */
    fun updateFlFormToFullFreeScreen(){

        fl_form.setPadding(0,0,0,0)

        val layoutParams = (fl_form.layoutParams as FrameLayout.LayoutParams)
        layoutParams.gravity = Gravity.NO_GRAVITY
        layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT
    }
}
