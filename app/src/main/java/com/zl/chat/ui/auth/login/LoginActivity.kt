package com.zl.chat.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.zl.chat.R
import com.zl.chat.ui.auth.AuthRepository
import com.zl.chat.ui.main.MainActivity
import com.zl.core.BaseApplication
import com.zl.core.base.ViewModelActivity
import com.zl.core.extend.toTextString
import com.zl.core.utils.SPUtils.savePrivateSharedPreferences
import kotlinx.android.synthetic.main.activity_login.*

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

    override fun setListener() {
        super.setListener()

        loginButton.setOnClickListener {
            viewModel.login(accountEdit.toTextString(), pwdEdit.toTextString())
        }
    }

    override fun observe() {
        super.observe()

        viewModel.userData.observe(this, Observer { user ->
            user?.let {
                BaseApplication.instance.user = it
                savePrivateSharedPreferences {
                    putString(BaseApplication.SP_ACCOUNT_ID, it.id)
                }
                MarsServiceProxy.inst.setAccountId(BaseApplication.instance.user?.id)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        viewModel.errorMsg.observe(this, Observer {
            showToastSafe(it.message)
        })
    }
}