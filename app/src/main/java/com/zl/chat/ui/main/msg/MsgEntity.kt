package com.zl.chat.ui.main.msg

import android.os.Parcel
import android.os.Parcelable

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/30 19:22.<br/>
 */
class MsgEntity() :Parcelable {

    var conversationId: String? = null
    var nickName: String? = null
    var headUrl: String? = null
    var lastTime: Long? = null

    constructor(parcel: Parcel) : this() {
        conversationId = parcel.readString()
        nickName = parcel.readString()
        headUrl = parcel.readString()
        lastTime = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(conversationId)
        parcel.writeString(nickName)
        parcel.writeString(headUrl)
        parcel.writeValue(lastTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MsgEntity> {
        override fun createFromParcel(parcel: Parcel): MsgEntity {
            return MsgEntity(parcel)
        }

        override fun newArray(size: Int): Array<MsgEntity?> {
            return arrayOfNulls(size)
        }
    }
}