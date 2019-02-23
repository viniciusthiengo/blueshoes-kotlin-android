package thiengo.com.br.blueshoes.view


import android.content.ActivityNotFoundException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_contact.*

import thiengo.com.br.blueshoes.R
import android.content.Intent
import android.net.Uri
import android.widget.Toast


class ContactFragment :
    Fragment(),
    View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        return inflater
            .inflate(
                R.layout.fragment_contact,
                container,
                false
            )
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        iv_phone_cities.setOnClickListener( this )
        tv_phone_cities.setOnClickListener( this )
        iv_phone_other_regions.setOnClickListener( this )
        tv_phone_other_regions.setOnClickListener( this )

        iv_email_orders.setOnClickListener( this )
        tv_email_orders.setOnClickListener( this )
        iv_email_attendance.setOnClickListener( this )
        tv_email_attendance.setOnClickListener( this )

        iv_address.setOnClickListener( this )
        tv_address.setOnClickListener( this )
    }

    override fun onClick(v: View) {

        when( v.id ){
            iv_phone_cities.id,
            tv_phone_cities.id -> {
                /*
                 * O número de telefone está sendo concatenado com o
                 * "0", pois como se trata de um número fixo local é
                 * aguardado o DDD da região logo depois de um "0".
                 * */
                phoneCallIntent( "0${tv_phone_cities.text}" )
            }
            iv_phone_other_regions.id,
            tv_phone_other_regions.id ->
                phoneCallIntent( tv_phone_other_regions.text.toString() )

            iv_email_orders.id,
            tv_email_orders.id ->
                mailToIntent( tv_email_orders.text.toString() )
            iv_email_attendance.id,
            tv_email_attendance.id ->
                mailToIntent( tv_email_attendance.text.toString() )

            iv_address.id,
            tv_address.id ->
                mapsRouteIntent( getString( R.string.contact_frag_address_formatted_to_google_maps ) )
        }
    }

    private fun phoneCallIntent( phoneNumber: String ){
        /*
         * O replace() está sendo utilizado para remover
         * caracteres que não são aceitos no Intent de
         * data "tel:"
         * */
        val phone = phoneNumber.replace( "(\\s|\\)|\\()", "" )
        val intent = Intent( Intent.ACTION_DIAL )

        intent.data = Uri.parse( "tel:$phone" )

        /*
         * Aqui não há necessidade de um try{}catch{} para o
         * startActivity(), pois é improvável que o aplicativo de
         * telefonema não esteja presente no smartphone ou tablet
         * Android.
         * */
        activity!!.startActivity( intent )
    }

    private fun mailToIntent( emailAddress: String ){
        val intent = Intent( Intent.ACTION_SENDTO )

        intent.data = Uri.parse( "mailto:" )
        intent.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf( emailAddress )
        )

        try{
            activity!!.startActivity( intent )
        }
        catch ( e: ActivityNotFoundException ){
            Toast
                .makeText(
                    activity,
                    getString(R.string.info_email_app_install),
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }

    private fun mapsRouteIntent( address: String ){
        val location = Uri.encode( address )
        val navigation = "google.navigation:q=$location"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )

        if( intent.resolveActivity( activity!!.packageManager ) != null ){
            activity!!.startActivity( intent )
        }
        else{
            Toast
                .makeText(
                    activity,
                    getString(R.string.info_google_maps_install),
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity)
            .updateToolbarTitleInFragment( R.string.contact_frag_title )
    }
}
