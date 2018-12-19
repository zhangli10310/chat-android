package com.zl.chat.ui.splash

import android.Manifest
import android.os.Bundle
import com.tencent.mars.stn.StnLogic
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.xlog.Log
import com.zl.chat.R
import com.zl.core.base.BaseActivity
import com.zl.mars.remote.MarsTaskWrapper
import kotlinx.android.synthetic.main.activity_splash.*

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:32.<br/>
 */
class SplashActivity : BaseActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }

        img.setOnClickListener {
            MarsServiceProxy.inst.send(object : MarsTaskWrapper.Stub() {

                override fun getCmdId() = 9

                override fun getChannelSelect() = StnLogic.Task.ELong

                override fun getHost() = "192.168.63.45"

                override fun getCgiPath() = "/go"

                override fun req2buf(): ByteArray {
                    return "go".toByteArray()
                }

                override fun buf2resp(buf: ByteArray?): Int {
                    return 1
                }

                override fun onTaskEnd(errType: Int, errCode: Int) {
                    Log.i(TAG, "onTaskEnd: $errCode")
                }

            })
        }
    }
}