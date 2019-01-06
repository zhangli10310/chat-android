package com.zl.chat.ui.chat

import androidx.annotation.IntDef

/**
 * Created by zhangli on 2019/1/6,20:55<br/>
 */
class ChatMsgEntity {


    var id: String? = null
    var userId: String? = null
    var headUrl: String? = null
    var message:String? = null

    var type: Int = 0
    var detail: String? = null
}