package com.zl.chat.ui.main.contact

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.core.base.ViewModelFragment

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
class ContactFragment : ViewModelFragment<ContactViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ContactViewModel.Factory()).get(ContactViewModel::class.java)
    }

    override fun layoutId() = R.layout.fragment_contact

    override fun initView(savedInstanceState: Bundle?) {

    }
}