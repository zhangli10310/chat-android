package com.zl.chat.ui.main.msg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zl.core.base.BaseViewModel

/**
 * Created by zhangli on 2018/12/22,12:11<br/>
 */
class MsgViewModel : BaseViewModel() {


    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(MsgViewModel::class.java)) {
                return MsgViewModel() as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass, can only create MsgViewModel")
            }
        }
    }
}