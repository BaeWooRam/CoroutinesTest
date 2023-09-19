package com.example.coroutinetest.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.ArrayList

@Serializable
data class UserDetail(
    @SerialName("id") var id: Int,
    @SerialName("title") var title: String?,
    @SerialName("content") var content: String?,
    @SerialName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerialName("level") var level: String?,
    @SerialName("room") var room: String?,
    @SerialName("endTime") var endTime: String?,
    @SerialName("startTime") var startTime: String?
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(level)
        parcel.writeString(room)
        parcel.writeString(endTime)
        parcel.writeString(startTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDetail> {
        override fun createFromParcel(parcel: Parcel): UserDetail {
            return UserDetail(parcel)
        }

        override fun newArray(size: Int): Array<UserDetail?> {
            return arrayOfNulls(size)
        }
    }

}