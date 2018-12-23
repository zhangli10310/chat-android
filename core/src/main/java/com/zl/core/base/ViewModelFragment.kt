package com.zl.core.base

import androidx.annotation.CallSuper

/**
 * Created by zhangli on 2018/12/22,21:55<br/>
 */
abstract class ViewModelFragment<T : BaseViewModel>: ModeFragment() {

    protected lateinit var viewModel: T

    @CallSuper
    override fun observe() {
        initViewModel()


    }

    abstract fun initViewModel()

    override fun afterView() {}

    override fun setListener() {}
}