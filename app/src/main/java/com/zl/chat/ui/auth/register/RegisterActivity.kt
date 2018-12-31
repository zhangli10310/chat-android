package com.zl.chat.ui.auth.register

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.chat.ui.auth.AuthRepository
import com.zl.core.base.ViewModelActivity
import com.zl.core.extend.toTextString
import com.zl.core.extend.toTextStringOrNull
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : ViewModelActivity<RegisterViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, RegisterViewModel.Factory(AuthRepository.get()))
            .get(RegisterViewModel::class.java)
    }

    override fun layoutId() = R.layout.activity_register

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun setListener() {
        super.setListener()
        registerButton.setOnClickListener {
            viewModel.register(phoneEdit.toTextStringOrNull(), pwdEdit.toTextString())
        }
    }

    override fun observe() {
        super.observe()

        viewModel.accountId.observe(this, Observer {
            showToastSafe("注册成功, id$it")
            finish()
        })

        viewModel.errorMsg.observe(this, Observer {
            showToastSafe(it.message)
        })
    }

}
