package com.zl.core.base

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/21 16:39.<br/>
 */
open class BaseMessage {

    val from = "fixme"
    var to: String? = null

    var clientTime = System.currentTimeMillis() / 1000
    var serverTime: Long? = null
}