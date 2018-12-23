package com.zl.core.base

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/4 15:33.<br/>
 */
open class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    private var mToast: Toast? = null

    private val PERMISSION_REQUEST_CODE = 0x1001
    private var mPermissionReturn: ((Boolean) -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.WHITE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    fun showToastSafe(text: CharSequence, gravity: Int = Gravity.BOTTOM, xOffset: Int = 0, yOffset: Int = 0) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(text)
        }
        mToast!!.setGravity(gravity, xOffset, yOffset)
        runOnUiThread {
            mToast!!.show()
        }
    }

    protected fun requestPermission(permissions: Array<String>, permissionReturn: ((Boolean) -> Unit)? = null) {
        mPermissionReturn = permissionReturn

        var grant = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                grant = false
            }
        }

        if (!grant) {
            val dialog = AlertDialog.Builder(this)
                .setMessage("请授予权限")
                .setPositiveButton("确定") { _, _ ->
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
                }
                .create()
            dialog.show()
        } else {
            permissionReturn?.invoke(true)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                mPermissionReturn?.invoke(true)
            } else {
                // Permission Denied
                mPermissionReturn?.invoke(false)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun startActivity(intent: Intent) {
        try {
            super.startActivity(intent)
        } catch (e: Exception) {
            showToastSafe("启动失败")
            Log.i(TAG, "startActivity: failed $e")
        }
    }
}