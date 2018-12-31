package com.zl.chat.ui.auth

import com.zl.core.BuildConfig
import com.zl.core.api.ServiceGenerator
import com.zl.core.base.BaseEntity
import com.zl.core.db.user.User
import io.reactivex.Observable

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 18:01.<br/>
 */
class AuthRepository {

    private val TAG = AuthRepository::class.java.simpleName

    private var mApi: AuthApi = ServiceGenerator.createRxService(AuthApi::class.java, BuildConfig.API_URL)

    companion object {
        fun get(): AuthRepository {
            return Instance.repository
        }
    }

    private object Instance {
        val repository = AuthRepository()
    }

    fun login(account: String, password: String): Observable<BaseEntity<User?>> {
        return mApi.login(
            mapOf(
                Pair("account", account),
                Pair("password", password)
            )
        )
    }

    fun register(phoneNo: String?, pwd: String): Observable<BaseEntity<String?>>{
        return mApi.register(mapOf(
            Pair("phoneNo", phoneNo),
            Pair("password", pwd)
        ))
    }
}