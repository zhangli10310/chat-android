package com.zl.core.base

class BaseEntity<T> {

    var code: Int = -1  //成功就是0,其他都是不成功
    var data: T? = null
    var message: String? = null
}