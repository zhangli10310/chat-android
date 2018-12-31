package com.zl.chat.update

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApi {

    @Streaming
    @GET
    fun downloadFileWithFixedUrl(@Url url: String): Observable<ResponseBody>
}