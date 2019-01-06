package com.zl.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zl.core.base.BaseViewModel

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/6 17:13.<br/>
 */
class ChatViewModel: BaseViewModel() {


    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                return ChatViewModel() as T
            } else {
                throw RuntimeException("Cannot create an instance of $modelClass, can only create ChatViewModel")
            }
        }
    }
}