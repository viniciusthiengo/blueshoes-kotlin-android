package thiengo.com.br.blueshoes.view.config.deliveryaddress


import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_config_new_delivery_address.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.DeliveryAddress


class FormUpdateDeliveryAddressFragment :
    FormNewDeliveryAddressFragment() {

    override fun getLayoutResourceID()
        = R.layout.fragment_config_update_delivery_address

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        bt_nu_address.text = getString( R.string.update_delivery_address )

        bt_nu_address.setOnClickListener{
            callPasswordDialog()
        }

        fillForm()
    }

    private fun fillForm(){
        val address = arguments!!.getParcelable<DeliveryAddress>(
            DeliveryAddress.KEY
        )

        et_street.setText( address.street )
        et_number.setText( address.number.toString() )
        et_complement.setText( address.complement )
        et_zip_code.setText( address.zipCode )
        et_neighborhood.setText( address.neighborhood )
        et_city.setText( address.city )
        sp_state.setSelection( address.state )
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_delivery_address )
        )
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_nu_address.text =
            if( status )
                getString( R.string.update_delivery_address_going )
            else
                getString( R.string.update_delivery_address )
    }
}
