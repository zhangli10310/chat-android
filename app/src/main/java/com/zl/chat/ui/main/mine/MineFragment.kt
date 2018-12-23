package com.zl.chat.ui.main.mine

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.core.base.ViewModelFragment

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
class MineFragment : ViewModelFragment<MineViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, MineViewModel.Factory()).get(MineViewModel::class.java)
    }

    override fun layoutId() = R.layout.fragment_mine

    override fun initView(savedInstanceState: Bundle?) {

    }
}