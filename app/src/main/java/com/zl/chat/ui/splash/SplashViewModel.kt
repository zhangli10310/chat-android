package com.zl.chat.ui.splash

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zl.core.base.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by zhangli on 2018/12/21,23:52<br/>
 */
class SplashViewModel : BaseViewModel() {

    var jump: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun countDown(milliseconds: Long) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
            .subscribe {
                jump.postValue(true)
            }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                return SplashViewModel() as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass, can only create SplashViewModel")
            }
        }
    }
}