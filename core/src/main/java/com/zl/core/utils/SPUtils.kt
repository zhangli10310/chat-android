package com.zl.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.zl.core.BaseApplication

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 19:49.<br/>
 */
object SPUtils {

    private const val SP_NAME = "chat"

    fun savePrivateSharedPreferences(name: String = SP_NAME, put: SharedPreferences.Editor.() -> Unit) {
        val edit = BaseApplication.instance.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
        put(edit)
        edit.apply()
    }

    fun getPrivateSharedPreferences(name: String = SP_NAME): SharedPreferences {
        return BaseApplication.instance.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun Context.savePrivateSharedPreferences(name: String = SP_NAME, put: SharedPreferences.Editor.() -> Unit) {
        val editor = getSharedPreferences(name, Context.MODE_PRIVATE).edit()
        editor.put()
        editor.apply()
    }

    fun Context.getPrivateSharedPreferences(name: String = SP_NAME): SharedPreferences {
        return getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}