package thiengo.com.br.blueshoes.domain

import android.os.Parcel
import android.os.Parcelable

class User(
        val name: String,
        val image: Int,
        val status: Boolean = false
    ) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(image)
        writeInt((if (status) 1 else 0))
    }

    companion object {
        const val KEY = "user-key"

        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
