package thiengo.com.br.blueshoes.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
        val name: String,
        val image: Int,
        val status: Boolean = false
    ) : Parcelable {

    companion object {
        const val KEY = "user-key"
    }
}
