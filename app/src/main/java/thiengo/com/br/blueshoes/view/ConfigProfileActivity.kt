package thiengo.com.br.blueshoes.view

import android.Manifest
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
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import android.content.pm.ActivityInfo
import android.content.Intent
import android.app.Activity
import android.net.Uri
import android.util.Log
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import android.R.attr.data
import com.nguyenhoanglam.imagepicker.model.Config.EXTRA_IMAGES
import com.nguyenhoanglam.imagepicker.model.Config.RC_PICK_IMAGES
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image


class ConfigProfileActivity :
    FormActivity(),
    KeyboardUtils.OnSoftInputChangedListener,
    EasyPermissions.PermissionCallbacks {

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
        /*Toast
            .makeText(this, "TODO", Toast.LENGTH_SHORT)
            .show()*/

        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                    this,
                    195,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                //.setRationale(R.string.camera_and_location_rationale)
                //.setPositiveButtonText(R.string.rationale_ask_ok)
                //.setNegativeButtonText(R.string.rationale_ask_cancel)
                //.setTheme(R.style.my_fancy_style)
                .build()
        )
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, list: List<String>) {
        /*Toast
            .makeText(this, "OK", Toast.LENGTH_SHORT)
            .show()*/

        /*Matisse.from(this)
            .choose(MimeType.ofImage())
            .capture(true)
            .captureStrategy(
                CaptureStrategy(
                    true,
                    "br.com.kroton.gnaction",
                    CAPTURE_FOLDER
                )
            )
            .countable(true)
            .maxSelectable(1)
            // .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
            // .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(196)*/

        ImagePicker.with(this)                         //  Initialize ImagePicker with activity or fragment context
            //.setToolbarColor("#212121")         //  Toolbar color
            //.setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
            //.setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
            //.setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
            //.setProgressBarColor("#4CAF50")     //  ProgressBar color
            //.setBackgroundColor("#212121")      //  Background color
            //.setCameraOnly(false)               //  Camera mode
            .setMultipleMode(false)              //  Select multiple images or single image
            .setFolderMode(true)                //  Folder mode
            .setShowCamera(true)                //  Show camera button
            .setFolderTitle("Galeria")           //  Folder title (works with FolderMode = true)
            .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
            .setDoneTitle("Finalizar")               //  Done button title
            .setLimitMessage("Apenas uma imagem pode ser selecionada.")    // Selection limit message
            .setMaxSize(1)                     //  Max images can be selected
            .setSavePath("image-picker")         //  Image capture folder name
            //.setSelectedImages(images)          //  Selected images
            //.setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
            .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
            .setKeepScreenOn(true)              //  Keep screen on when selecting images
            .start();                           //  Start ImagePicker
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast
            .makeText(this, "NO", Toast.LENGTH_SHORT)
            .show()
    }

    var mSelected = listOf<Uri>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        /*if (requestCode == 196 && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data!!)
            Log.d("Matisse", "mSelected: $mSelected")
        }*/

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null) {
            val images = data.getParcelableArrayListExtra<Image>(Config.EXTRA_IMAGES)

            Toast
                .makeText(this, "Image number: ${images.size}", Toast.LENGTH_SHORT)
                .show()
            // do your logic here...
        }
        super.onActivityResult(requestCode, resultCode, data)  // You MUST have this line to be here
        // so ImagePicker can work with fragment mode
    }
}