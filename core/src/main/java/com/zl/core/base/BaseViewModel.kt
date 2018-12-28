package com.zl.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zl.core.NetException

/**
 *
 *<p></p>
 *
 * Created by zhangli<br/>
 */
open class BaseViewModel : ViewModel() {

    var errorMsg = MutableLiveData<NetException>()

}