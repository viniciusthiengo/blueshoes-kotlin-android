package thiengo.com.br.blueshoes.view


import android.graphics.Typeface
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.text.Annotation
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_privacy_policy.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.util.CustomTypefaceSpan


class PrivacyPolicyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater
            .inflate(
                R.layout.fragment_privacy_policy,
                container,
                false
            )
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        /*
         * Vamos manter a invocação do método privacyPolicyLoad()
         * em um método que vem depois do método onCreateActivity()
         * para assim podermos manter a sintaxe
         * kotlin-android-extensions de acesso às Views do layout
         * do fragmento.
         * */
        privacyPolicyLoad()
    }

    private fun privacyPolicyLoad(){

        /*
         * Aplicando o casting de CharSequence para SpannedString
         * para que seja possível acessar as Spans presentes no
         * texto.
         *
         * É preciso utilizar getText(), que retorna um CharSequence.
         * getString() retorna uma String e Strings não contém Spans.
         * */
        val text = getText( R.string.privacy_policy_content ) as SpannedString

        /*
         * Obtendo todas as Annotation Span do texto.
         * */
        val annotations = text.getSpans(
            0, /* Posição inicial do texto. */
            text.length, /* Posição final do texto. */
            Annotation::class.java /* Classe Span (ParcelableSpan) para recolher objetos do tipo. */
        )

        /*
         * Criando uma cópia do texto, com SpannableString,
         * para que seja possível adicionar ou remover Span além
         * de também ser possível adicionar ou remover caracteres.
         * */
        val spannedText = SpannableString( text )

        /*
         * Iterando sobre todas as annotations encontradas.
         * */
        for( annotation in annotations ){

            val textStartPos = text.getSpanStart( annotation )
            val textEndPos = text.getSpanEnd( annotation )
            val spanFlag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

            if( annotation.key.equals("title") ){ /* Títulos. */
                /*
                 * Todos os títulos têm uma fonte customizada
                 * aplicada a eles, mais precisamente a fonte
                 * Pathway Gothic One.
                 * */
                val typeFace = ResourcesCompat
                    .getFont(
                        activity!!,
                        R.font.pathway_gothic_one_regular
                    )
                spannedText.setSpan(
                    CustomTypefaceSpan( typeFace!! ),
                    textStartPos,
                    textEndPos,
                    spanFlag
                )

                /*
                 * Todos os títulos têm o estilo negrito aplicado a
                 * eles.
                 * */
                spannedText.setSpan(
                    StyleSpan( Typeface.BOLD ),
                    textStartPos,
                    textEndPos,
                    spanFlag
                )

                /*
                 * Obtendo o tamanho correto do texto de acordo com a
                 * AnnotationSpan de título presente nele.
                 * */
                val textSizeSpan = when( annotation.value ){
                    "main" -> RelativeSizeSpan( 1.5F )
                    "sub" -> RelativeSizeSpan( 1.3F )
                    else -> RelativeSizeSpan( 1.1F ) /* "sub_sub" */
                }
                spannedText.setSpan(
                    textSizeSpan,
                    textStartPos,
                    textEndPos,
                    spanFlag
                )
            }
            else if( annotation.key.equals("link") ){ /* Links. */

                /*
                 * Os "+ 1" e "- 1" sendo utilizados é
                 * para evitar que trechos que não fazem
                 * parte do texto de link sejam
                 * adicionados ao link.
                 * */
                spannedText.setSpan(
                    URLSpan( annotation.value ),
                    textStartPos + 1,
                    textEndPos - 1,
                    spanFlag
                )

                /*
                 * Colocando a cor de link utilizado
                 * neste projeto Android, pois este é
                 * um trecho de âncora dentro das
                 * políticas de privacidade.
                 * */
                spannedText.setSpan(
                    ForegroundColorSpan(
                        ContextCompat
                            .getColor(
                                activity!!,
                                R.color.colorLink
                            )
                    ),
                    textStartPos + 1,
                    textEndPos - 1,
                    spanFlag
                )
            }
        }

        /*
         * Para que o trecho de texto com Span de link externo
         * possa ser clicável. É preciso configurar um
         * LinkMovementMethod.
         * */
        tv_privacy_policy_content.movementMethod = LinkMovementMethod.getInstance()

        /*
         * Colocando o texto estilizado no TextView de
         * Políticas de Privacidade.
         *
         * A invocação do método trimStart() a seguir se
         * faz necessária como uma estratégia para remover
         * o espaço em branco do início do texto de
         * Políticas de Privacidade no arquivo XML.
         * */
        tv_privacy_policy_content.text = spannedText.trimStart()
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity)
            .updateToolbarTitleInFragment( R.string.privacy_policy_frag_title )
    }
}
