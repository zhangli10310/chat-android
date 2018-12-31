package com.zl.chat.update

import com.zl.core.BuildConfig
import com.zl.core.api.ServiceGenerator
import com.zl.core.base.BaseEntity
import io.reactivex.Observable


class UpdateRepository {

    private var mService: UpdateApi = ServiceGenerator.createRxService(UpdateApi::class.java, BuildConfig.API_URL)

    companion object {
        fun get(): UpdateRepository {
            return Instance.repository
        }
    }

    private object Instance {
        val repository = UpdateRepository()
    }

    fun checkUpdate(): Observable<BaseEntity<UpdateResult?>> {
        return mService.checkUpdate(mapOf(

        ))
    }
}