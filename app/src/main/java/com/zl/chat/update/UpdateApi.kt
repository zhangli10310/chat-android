package com.zl.chat.update

import com.zl.core.base.BaseEntity
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface UpdateApi {

    @POST("")
    fun checkUpdate(@Body map: Map<String, String>): Observable<BaseEntity<UpdateResult?>>

}