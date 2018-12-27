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

    const val SP_NAME = "chat"

    fun save(put: SharedPreferences.Editor.() -> Unit) {
        val edit = BaseApplication.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
        put(edit)
        edit.apply()
    }

    fun Context.saveDefaultSharedPreferences(put: SharedPreferences.Editor.() -> Unit) {
        val editor = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
        editor.put()
        editor.apply()
    }
}