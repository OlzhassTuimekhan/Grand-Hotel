package kz.grand_hotel.ui.menu.ui.message

import android.os.Parcel
import android.os.Parcelable

data class MessageItem(
    val profileImageResId: Int,
    val name: String,
    val lastMessage: String,
    val time: String,
    val notificationCount: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(profileImageResId)
        parcel.writeString(name)
        parcel.writeString(lastMessage)
        parcel.writeString(time)
        parcel.writeInt(notificationCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageItem> {
        override fun createFromParcel(parcel: Parcel): MessageItem {
            return MessageItem(parcel)
        }

        override fun newArray(size: Int): Array<MessageItem?> {
            return arrayOfNulls(size)
        }
    }
}
