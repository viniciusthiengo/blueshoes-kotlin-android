package thiengo.com.br.blueshoes.view.config.deliveryaddress


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thiengo.com.br.blueshoes.R


/*
 * Fragmento com responsabilidade de ser o fragmento
 * host de mais de um fragmento e assim permitir a
 * fácil alternância de fragmentos dentro de uma mesma
 * tela de ViewPager.
 * */
class ConfigDeliveryAddressHostFragment
    : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        /*
         * É preciso inflar o layout que vai conter
         * os fragmentos.
         * */
        val view = inflater
            .inflate(
                R.layout.fragment_config_delivery_address_host,
                container,
                false
            )

        /*
         * Somente na primeira abertura é que a regra de
         * fragmento inicial, do bloco condicional a seguir,
         * deve ser seguida.
         * */
        if( savedInstanceState == null ){
            val transaction = activity!!
                .supportFragmentManager!!
                .beginTransaction()

            /*
             * Então, aqui no fragmento root (container),
             * iniciamos com o primeiro fragmento via
             * FragmentTransaction e sem trabalho com pilha
             * de fragmentos.
             * */
            transaction
                .replace(
                    R.id.fl_root,
                    ConfigDeliveryAddressesListFragment()
                )
                .commit()
        }

        return view
    }
}
