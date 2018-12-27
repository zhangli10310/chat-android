package com.zl.core.base

import androidx.annotation.CallSuper


/**
 *
 *<p></p>
 *
 * Created by zhangli<br/>
 */
abstract class ViewModelActivity<T : BaseViewModel> : ModeActivity() {

    protected lateinit var viewModel: T

    @CallSuper
    override fun observe() {
        initViewModel()
    }

    abstract fun initViewModel()

    override fun afterView() {}

    override fun setListener() {}
}