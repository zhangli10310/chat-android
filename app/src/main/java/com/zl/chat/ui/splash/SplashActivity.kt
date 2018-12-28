package com.zl.chat.ui.splash

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.zl.chat.R
import com.zl.chat.service.NotificationService
import com.zl.chat.ui.auth.login.LoginActivity
import com.zl.chat.ui.main.MainActivity
import com.zl.core.BaseApplication
import com.zl.core.base.ViewModelActivity
import com.zl.core.db.AppDatabase
import com.zl.core.utils.SPUtils.getPrivateSharedPreferences
import kotlin.concurrent.thread

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:32.<br/>
 */
class SplashActivity : ViewModelActivity<SplashViewModel>() {

    private val TAG = SplashActivity::class.java.simpleName

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, SplashViewModel.Factory()).get(SplashViewModel::class.java)

        viewModel.jump.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }

    override fun layoutId() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun afterView() {
        super.afterView()

        startService(Intent(this, NotificationService::class.java))

        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {

            AlertDialog.Builder(this)
                .setMessage("请打开通知权限")
                .setPositiveButton("确定") { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", application.getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                }
                .setNegativeButton("不") { _, _ ->
                    checkPermission()
                }
                .show()
        } else {
            checkPermission()
        }
    }

    private fun checkPermission() {
        requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (it) {
                checkAccount()
            } else {
                finish()
            }
        }
    }

    private fun checkAccount() {

        if (BaseApplication.instance.user == null) {
            thread {
                val accountId = getPrivateSharedPreferences().getString(BaseApplication.SP_ACCOUNT_ID, null)
                if (accountId != null) {
                    val user = AppDatabase.getInstance().userDao().queryUserById(accountId)
                    runOnUiThread {
                        if (user == null) {
                            goToLogin()
                        } else {
                            BaseApplication.instance.user = user
                            goToMain()
                        }
                    }
                } else {
                    goToLogin()
                }
            }
        } else {
            goToMain()
        }
    }

    private fun goToMain() {
        MarsServiceProxy.inst.setAccountId(BaseApplication.instance.user?.id)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}