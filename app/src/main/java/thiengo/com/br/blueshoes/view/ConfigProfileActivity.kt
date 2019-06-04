package thiengo.com.br.blueshoes.view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.content_config_profile.*
import kotlinx.android.synthetic.main.content_forgot_password.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.User
import thiengo.com.br.blueshoes.util.validate


class ConfigProfileActivity :
    FormActivity(),
    KeyboardUtils.OnSoftInputChangedListener {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        KeyboardUtils.registerSoftInputChangedListener(
            this,
            this
        )


        et_name.validate(
            {
                it.length > 1
            },
            getString( R.string.invalid_name )
        )
        et_name.setOnEditorActionListener( this )

        /*
         * Name é um dos dados de banco de dados, e campo de
         * formulário, que nunca poderá estar vázio.
         * */
        val user = intent.getParcelableExtra<User>( User.KEY )
        et_name.setText( user.name )
    }

    override fun getLayoutResourceID()
        = R.layout.content_config_profile

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_config_profile )
        )
    }

    override fun blockFields( status: Boolean ){
        iv_profile.isEnabled = !status
        et_name.isEnabled = !status
        bt_send_profile.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_send_profile.text =
            if( status )
                getString( R.string.config_profile_going )
            else
                getString( R.string.config_profile )
    }

    fun callGallery( view: View ){
        Toast
            .makeText(this, "TODO", Toast.LENGTH_SHORT)
            .show()
    }


    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener( this )
        super.onDestroy()
    }

    override fun onSoftInputChanged( height: Int ) {
        if( isAbleToCallChangeTargetViewConstraints() ){
            changeTargetViewConstraints(
                KeyboardUtils.isSoftInputVisible( this )
            )
        }
    }

    private fun isAbleToCallChangeTargetViewConstraints()
            = true

    private fun changeTargetViewConstraints(
        isKeyBoardOpened: Boolean
    ){

        val photoProfileId = iv_profile.id
        val parent = iv_profile.parent as ConstraintLayout
        val constraintSet = ConstraintSet()
        val size = (108 * ScreenUtils.getScreenDensity()).toInt()

        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(
            photoProfileId,
            size
        )
        constraintSet.constrainHeight(
            photoProfileId,
            size
        )

        /*
         * Centralizando a View horizontalmente no
         * ConstraintLayout.
         * */
        constraintSet.centerHorizontally(
            photoProfileId,
            ConstraintLayout.LayoutParams.PARENT_ID
        )

        if( isConstraintToSiblingView( isKeyBoardOpened ) ){
            setConstraintsRelativeToSiblingView( constraintSet, photoProfileId )
        }
        else{
            constraintSet.connect(
                photoProfileId,
                ConstraintLayout.LayoutParams.TOP,
                ConstraintLayout.LayoutParams.PARENT_ID,
                ConstraintLayout.LayoutParams.TOP
            )
        }

        constraintSet.applyTo( parent )
    }

    private fun isConstraintToSiblingView(
        isKeyBoardOpened: Boolean
    ): Boolean {

        return isKeyBoardOpened || ScreenUtils.isLandscape()
    }

    private fun setConstraintsRelativeToSiblingView(
        constraintSet: ConstraintSet,
        targetViewId: Int
    ) {

        constraintSet.connect(
            targetViewId,
            ConstraintLayout.LayoutParams.BOTTOM,
            tv_name.id,
            ConstraintLayout.LayoutParams.TOP,
            (30 * ScreenUtils.getScreenDensity()).toInt()
        )
    }
}