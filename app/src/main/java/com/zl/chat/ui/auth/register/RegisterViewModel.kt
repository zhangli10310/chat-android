package com.zl.chat.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zl.chat.ui.auth.AuthRepository
import com.zl.core.NetException
import com.zl.core.base.BaseViewModel
import com.zl.core.extend.apiSubscribe

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/29 10:32.<br/>
 */
class RegisterViewModel(private var repository: AuthRepository) : BaseViewModel() {

    val accountId = MutableLiveData<String>()

    fun register(phoneNo: String?, pwd: String) {
        repository.register(phoneNo, pwd)
            .apiSubscribe(this, {
                accountId.postValue(it)
            }, {
                errorMsg.postValue(NetException(-1, it.message.toString()))
            })
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private var repository: AuthRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(repository) as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass, can only create LoginViewModel")
            }
        }
    }
}