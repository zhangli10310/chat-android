package com.zl.core.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/24 15:24.<br/>
 */
@Entity
class User {

    @PrimaryKey
    var id: String = "unknown"

    var nickName: String? = null

    var phoneNo: String? = null

    var sex: Int? = null // 0 男  1 女

    var city: String? = null
}