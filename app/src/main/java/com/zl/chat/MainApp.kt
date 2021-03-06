package com.zl.chat

import android.os.Environment
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.wrapper.service.CoreService
import com.tencent.mars.wrapper.service.DefaultMarsServiceProfile
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import com.zl.core.BackgroundActivityLifecycleCallbacks
import com.zl.core.BaseApplication
import com.zl.core.BuildConfig


/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:29.<br/>
 */
class MainApp : BaseApplication() {

    private val TAG = MainApp::class.java.simpleName

    var foreground = false

    companion object {
        lateinit var instance: MainApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        initXlogEnv()

        CoreService.setProfileFactory {
            object :DefaultMarsServiceProfile() {
                override fun longLinkHost(): String {
                    return BuildConfig.HOST_IP
                }
            }
        }

        MarsServiceProxy.init(this)

        registerActivityLifecycleCallbacks(BackgroundActivityLifecycleCallbacks({
            //onBackground
            Log.i(TAG, "app background")

            foreground = false
            MarsServiceProxy.inst.setForeground(false)

            Log.appenderClose()
        }, {
            //onResume
            Log.i(TAG, "app resume")
            foreground = true
            MarsServiceProxy.inst.setForeground(true)
        }))

        Log.i(TAG, "application started")
    }

    private fun initXlogEnv() {
        val sdcard = Environment.getExternalStorageDirectory().getAbsolutePath()
        val logPath = "$sdcard/${BuildConfig.APP_DIR}/log"
        val cachePath = "$filesDir/${BuildConfig.APP_DIR}/xlog"
        if (BuildConfig.DEBUG) {
            Xlog.open(true, Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "", "")
            Xlog.setConsoleLogOpen(true)

        } else {
            Xlog.open(true, Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, "", "")
            Xlog.setConsoleLogOpen(false)
        }

        Log.setLogImp(Xlog())

    }
}