package thiengo.com.br.blueshoes.view.config.deliveryaddress


import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_config_new_delivery_address.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.view.FormFragment
import thiengo.com.br.blueshoes.view.config.ConfigFormFragment


open class FormNewDeliveryAddressFragment :
    ConfigFormFragment() {

    companion object{
        /*
         * A constante abaixo representa a posição
         * deste fragmento no ViewPager. Os
         * posicionamentos em ViewPager começam
         * em 0.
         * */
        const val  PAGER_POS = 1
    }

    override fun title()
        = R.string.config_delivery_address_tab_new

    override fun getLayoutResourceID()
        = R.layout.fragment_config_new_delivery_address

    override fun onActivityCreated( savedInstanceState: Bundle? ){
        super.onActivityCreated( savedInstanceState )

        updateFlFormToFullFreeScreen()

        bt_nu_address.setOnClickListener{
            /*
             * O método mainAction() é invocado no lugar
             * de callPasswordDialog(), pois aqui não há
             * necessidade de dialog de senha para a
             * adição de endereço de entrega.
             * */
            mainAction()
        }
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_delivery_address )
        )
    }

    override fun blockFields( status: Boolean ){
        et_street.isEnabled = !status
        et_number.isEnabled = !status
        et_complement.isEnabled = !status
        et_zip_code.isEnabled = !status
        et_neighborhood.isEnabled = !status
        et_city.isEnabled = !status
        sp_state.isEnabled = !status
        bt_nu_address.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_nu_address.text =
            if( status )
                getString( R.string.add_delivery_address_going )
            else
                getString( R.string.add_delivery_address )
    }
}
