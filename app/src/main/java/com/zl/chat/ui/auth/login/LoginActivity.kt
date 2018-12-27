package com.zl.chat.ui.auth.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.chat.ui.auth.AuthRepository
import com.zl.core.BaseApplication
import com.zl.core.base.ViewModelActivity

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 11:08.<br/>
 */
class LoginActivity : ViewModelActivity<LoginViewModel>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, LoginViewModel.Factory(AuthRepository.get()))
            .get(LoginViewModel::class.java)
    }

    override fun layoutId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun observe() {
        super.observe()

        viewModel.userData.observe(this, Observer { user ->
            user?.let {
                BaseApplication.instance.user = it
            }
        })
    }
}