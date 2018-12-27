package com.zl.chat.ui.auth

import com.zl.core.base.BaseEntity
import com.zl.core.db.user.User
import io.reactivex.Observable
import retrofit2.http.POST

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 11:02.<br/>
 */
interface AuthApi {

    @POST("auth/login")
    fun login(map: Map<String, String>): Observable<BaseEntity<User?>>
}