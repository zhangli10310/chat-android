package com.zl.core

import androidx.multidex.MultiDexApplication
import com.zl.core.db.user.User

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:26.<br/>
 */
open class BaseApplication : MultiDexApplication() {

    var user: User? = null

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}