package com.zl.chat.ui.main

import android.os.Bundle
import com.tencent.mars.wrapper.remote.LongLinkMarsTaskAdapter
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.zl.chat.R
import com.zl.core.base.BaseActivity
import com.zl.core.base.BaseMessage
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/21 16:19.<br/>
 */
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout.setOnClickListener {
            MarsServiceProxy.inst.send(object : LongLinkMarsTaskAdapter() {

                override fun getCmdId() = 10

                override fun request(): Any {
                    return BaseMessage().apply {
                        to = "all"
                    }
                }

                override fun onResponse(json: String) {

                }

            })
        }
    }
}