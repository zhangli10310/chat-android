package com.zl.core

import androidx.multidex.MultiDexApplication
import com.zl.core.db.AppDatabase
import com.zl.core.db.user.User
import com.zl.core.utils.SPUtils.getPrivateSharedPreferences
import kotlin.concurrent.thread

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:26.<br/>
 */
open class BaseApplication : MultiDexApplication() {

    var user: User? = null

    companion object {
        const val SP_ACCOUNT_ID = "accountId"
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val accountId = getPrivateSharedPreferences().getString(SP_ACCOUNT_ID, null)
        if (accountId != null) {
            thread {
                user = AppDatabase.getInstance().userDao().queryUserById(accountId)
            }
        }
    }
}