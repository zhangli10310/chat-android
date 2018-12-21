package com.zl.chat.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.zl.chat.R
import com.zl.chat.service.NotificationService
import com.zl.chat.ui.main.MainActivity
import com.zl.core.base.BaseActivity

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

        startService(Intent(this, NotificationService::class.java))

        requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}