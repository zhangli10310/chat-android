package com.zl.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class BackgroundActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private val mHandler = Handler(Looper.getMainLooper())
    private var check: Runnable? = null
    var isInForeground = true
        private set
    private var mPaused = true

    private var onBackground: (() -> Unit)? = null
    private var onResume: (() -> Unit)? = null

    constructor(onBackground: (() -> Unit)?, onResume: (() -> Unit)?) {
        this.onBackground = onBackground
        this.onResume = onResume
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityPaused(activity: Activity) {
        mPaused = true

        if (check != null) {
            mHandler.removeCallbacks(check)
        }
        check = Runnable {
            if (isInForeground && mPaused) {
                isInForeground = false
                onBackground?.invoke()
            }
        }

        mHandler.postDelayed(check, 500L)

    }

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {
        mPaused = false
        if (!isInForeground) {
            onResume?.invoke()
        }
        isInForeground = true
        if (check != null) {
            mHandler.removeCallbacks(check)
        }
    }

    override fun onActivityStopped(activity: Activity) {}

}