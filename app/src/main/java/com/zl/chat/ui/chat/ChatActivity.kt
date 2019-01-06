package com.zl.chat.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.mars.wrapper.Constant
import com.tencent.mars.wrapper.remote.LongLinkJsonTaskAdapter
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.wrapper.remote.PushMessageHandler
import com.tencent.mars.xlog.Log
import com.zl.chat.R
import com.zl.core.BaseApplication
import com.zl.core.base.BaseMessage
import com.zl.core.base.ViewModelActivity
import kotlinx.android.synthetic.main.activity_chat.*


/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/6 17:02.<br/>
 */
class ChatActivity: ViewModelActivity<ChatViewModel>() {

    private val TAG = ChatActivity::class.java.simpleName

    private val mList = mutableListOf<String>()
    private lateinit var mAdapter:ChatMsgAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ChatViewModel.Factory()).get(ChatViewModel::class.java)
    }

    override fun layoutId() = R.layout.activity_chat

    override fun initView(savedInstanceState: Bundle?) {

        mAdapter = ChatMsgAdapter(mList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }

    override fun setListener() {
        super.setListener()

        sendButton.setOnClickListener {
            MarsServiceProxy.inst.send(object :LongLinkJsonTaskAdapter(){
                override fun getCmdId() = Constant.CID_SEND_SINGLE_TEXT_MSG

                override fun request(): Any {
                    return BaseMessage().apply {
                        from = BaseApplication.instance.user!!.id
                        to = "all"
                    }
                }

                override fun onResponse(json: String) {
                    Log.i(TAG, "onResponse: " + json + Thread.currentThread())
                }

            })
        }
    }

    val handler = PushMessageHandler {

        runOnUiThread {
            mList.add(it.messageString)
            mAdapter.notifyDataSetChanged()
        }
    }
    override fun onResume() {
        super.onResume()

        MarsServiceProxy.inst.addPushMessageHandler(handler)
    }

    override fun onPause() {
        super.onPause()
        MarsServiceProxy.inst.removePushMessageHandler(handler)
    }
}