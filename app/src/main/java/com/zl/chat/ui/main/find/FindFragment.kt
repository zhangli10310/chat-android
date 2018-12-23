package com.zl.chat.ui.main.find

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.core.base.ViewModelFragment

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
class FindFragment : ViewModelFragment<FindViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, FindViewModel.Factory()).get(FindViewModel::class.java)
    }

    override fun layoutId() = R.layout.fragment_find

    override fun initView(savedInstanceState: Bundle?) {

    }
}