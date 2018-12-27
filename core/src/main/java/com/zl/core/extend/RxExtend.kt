package com.zl.core.extend

import com.zl.core.base.BaseEntity
import com.zl.core.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/27 18:10.<br/>
 */
public fun <T> Observable<BaseEntity<T?>>.apiSubscribe(viewModel: BaseViewModel, onNext: (T?) -> Unit, onError: ((Throwable) -> Unit)? = null): Disposable {

    return subscribeOn(Schedulers.io())
//            .doOnSubscribe {
//                viewModel.isLoading.postValue(true)
//            }
//            .doOnTerminate {
//                viewModel.isLoading.postValue(false)
//            }
        .subscribe({
            if (it.code == 0) {
                onNext(it.data)
            } else {
//                    viewModel.errorMsg.postValue(NetError(it.status_code, "fixme: RxExtend.kt line-29"))
                if (onError != null) {
                    onError(RuntimeException(it.message.toString()))
                }
            }
        }, {
            if (onError == null) {
                it.printStackTrace()
//                    viewModel.errorMsg.postValue(NetError(-1, it.message))
            } else {
                onError(it)
            }
        })

}