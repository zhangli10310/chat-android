package com.zl.chat.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tencent.mars.xlog.Log
import com.zl.chat.ui.auth.AuthRepository
import com.zl.core.base.BaseViewModel
import com.zl.core.db.AppDatabase
import com.zl.core.db.user.User
import com.zl.core.extend.apiSubscribe

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 18:21.<br/>
 */
class LoginViewModel(private var repository: AuthRepository) : BaseViewModel() {

    private val TAG = LoginViewModel::class.java.simpleName

    var userData = MutableLiveData<User?>()

    fun login(account: String, password: String) {
        repository.login(account, password)
            .apiSubscribe(this, onNext = {
                userData.postValue(it)
                if (it != null) {
                    AppDatabase.getInstance().userDao().insert(it)
                }
            }, onError = {
                Log.i(TAG, "login: ${it.message}")
            })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private var repository: AuthRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(repository) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass, can only create LoginViewModel")
            }
        }
    }
}