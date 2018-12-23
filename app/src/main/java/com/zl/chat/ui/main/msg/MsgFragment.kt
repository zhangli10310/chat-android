package com.zl.chat.ui.main.msg

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.core.base.ViewModelFragment

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
class MsgFragment : ViewModelFragment<MsgViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, MsgViewModel.Factory()).get(MsgViewModel::class.java)
    }

    override fun layoutId() = R.layout.fragment_msg

    override fun initView(savedInstanceState: Bundle?) {

    }
}