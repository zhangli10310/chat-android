package com.zl.chat.ui.chat

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.mars.wrapper.Constant
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.wrapper.remote.PushMessageHandler
import com.tencent.mars.wrapper.remote.msg.SingleTextMessageTask
import com.zl.chat.MainApp
import com.zl.chat.R
import com.zl.chat.common.TextMessage
import com.zl.core.base.ViewModelActivity
import com.zl.core.extend.clear
import com.zl.core.extend.toTextString
import kotlinx.android.synthetic.main.activity_chat.*


/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/6 17:02.<br/>
 */
class ChatActivity : ViewModelActivity<ChatViewModel>() {

    private val TAG = ChatActivity::class.java.simpleName

    private val mList = mutableListOf<ChatMsgEntity>()
    private lateinit var mAdapter: ChatMsgAdapter

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
            val msgText = inputEdit.toTextString()
            MarsServiceProxy.inst.send(object : SingleTextMessageTask() {

                override fun onSendFail(id: String) {

                }

                override fun request(): Any {
                    return TextMessage().apply {
                        id = getId()
                        from = MainApp.instance.user?.id!!
                        to = "all"
                        msg = msgText
                    }
                }

                override fun onSendSuccess(id: String) {

                }

            })
            inputEdit.clear()
        }
    }

    val handler = PushMessageHandler {

        if (it.cmdId == Constant.CID_RECEIVE_SINGLE_TEXT_MSG) {
            val msg = it.toMessage(TextMessage::class.java)
            runOnUiThread {
                val entity = ChatMsgEntity().apply {
                    userId = msg.from
                    message = msg.msg
                }
                mList.add(entity)
                mAdapter.notifyDataSetChanged()
                recyclerView.smoothScrollToPosition(mList.size - 1)
            }
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