package thiengo.com.br.blueshoes.view

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.content_config_profile.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.domain.User
import thiengo.com.br.blueshoes.util.validate
import android.content.Intent
import android.app.Activity
import android.util.Log
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import android.graphics.Color
import android.os.Environment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.nguyenhoanglam.imagepicker.model.Config.EXTRA_IMAGES
import com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES
import com.nguyenhoanglam.imagepicker.model.Image
import com.squareup.picasso.Picasso
import java.io.File
import android.R.attr.bottomLeftRadius
import android.R.attr.bottomRightRadius
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.blankj.utilcode.util.ColorUtils
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Canvas


class ConfigProfileActivity :
    FormActivity(),
    KeyboardUtils.OnSoftInputChangedListener {

    val rivSize = (108 * ScreenUtils.getScreenDensity()).toInt()

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
        riv_profile.isEnabled = !status
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

        val photoProfileId = riv_profile.id
        val parent = riv_profile.parent as ConstraintLayout
        val constraintSet = ConstraintSet()
        //val size = rivSize

        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(
            photoProfileId,
            rivSize
        )
        constraintSet.constrainHeight(
            photoProfileId,
            rivSize
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

    fun callGallery( view: View ){

        val colorPrimary = ColorUtils.int2ArgbString( ColorUtils.getColor(R.color.colorPrimary) )
        val colorPrimaryDark = ColorUtils.int2ArgbString( ColorUtils.getColor(R.color.colorPrimaryDark) )
        val colorText = ColorUtils.int2ArgbString( ColorUtils.getColor(R.color.colorText) )
        val colorWhite = ColorUtils.int2ArgbString( Color.WHITE )

        ImagePicker
            .with(this) /* Inicializa a ImagePicker API com um context (Activity ou Fragment) */
            .setToolbarColor( colorPrimary )
            .setStatusBarColor( colorPrimaryDark )
            .setToolbarTextColor( colorText )
            .setToolbarIconColor( colorText )
            .setProgressBarColor( colorPrimaryDark )
            .setBackgroundColor( colorWhite )
            .setMultipleMode( false )
            .setFolderMode( true )
            .setShowCamera( true )
            .setFolderTitle( getString(R.string.imagepicker_gallery_activity) ) /* Nome da tela de galeria da ImagePicker API (funciona quando FolderMode = true). */
            .setLimitMessage( getString(R.string.imagepicker_selection_limit) )
            .setSavePath( getString(R.string.imagepicker_cam_photos_activity) ) /* Folder das imagens de câmera, tiradas a partir da ImagePicker API. */
            .setKeepScreenOn( true ) /* Mantém a tela acionada enquanto a galeria estiver aberta. */
            .start()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent? ) {

        if( requestCode == RC_PICK_IMAGES
            && resultCode == Activity.RESULT_OK
            && data != null ){

            val images = data.getParcelableArrayListExtra<Image>( EXTRA_IMAGES )

            if( images.isNotEmpty() ){

                /*
                 * Removendo android:tint, caso contrário a
                 * imagem escolhida pelo usuário fica toda
                 * na cor definida como valor de android:tint.
                 * */
                riv_profile.setColorFilter(Color.TRANSPARENT)

                /*
                 * Primeiro, é necessário transformar o path String da
                 * imagem em um path File para que ela seja carregada
                 * com a Picasso API (para mim a melhor API para
                 * carregamento de imagens remotas e locais).
                 *
                 * Segundo, os métodos resize() e centerCrop() precisam
                 * ser invocados no carregamento de imagens que vieram
                 * da API ImagePicker quando a API de carregamento é a
                 * Picasso API, isso é apenas uma regra de negócio
                 * definida para a ImagePicker API pelos desenvolvedores
                 * desta library.
                 * */
                Picasso
                    .get()
                    .load( File( images.first().path ) )
                    .resize( rivSize, rivSize )
                    .centerCrop()
                    .into( riv_profile )
            }
        }

        /*
         * Note que em nossa lógica de negócio, se não houver imagem
         * selecionada, o que estiver atualmente presente como imagem
         * de perfil continua sendo a imagem de perfil.
         * */

        /*
         * A invocação a super.onActivityResult() tem que
         * vir após a verificação / obtenção da imagem.
         * */
        super.onActivityResult( requestCode, resultCode, data )
    }
}