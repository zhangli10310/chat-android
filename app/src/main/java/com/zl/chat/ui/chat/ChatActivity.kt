package com.zl.chat.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.mars.wrapper.Constant
import com.tencent.mars.wrapper.remote.MarsServiceProxy
import com.tencent.mars.wrapper.remote.PushMessageHandler
import com.tencent.mars.wrapper.remote.msg.SingleTextMessageTask
import com.zl.chat.MainApp
import com.zl.chat.R
import com.zl.chat.common.TextMessage
import com.zl.chat.ui.main.msg.MsgEntity
import com.zl.core.base.ViewModelActivity
import com.zl.core.extend.clear
import com.zl.core.extend.isEmpty
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

    private lateinit var mConversationInfo: MsgEntity

    private val mList = mutableListOf<ChatMsgEntity>()
    private lateinit var mAdapter: ChatMsgAdapter

    companion object {
        fun startActivity(activity: Activity, msgEntity: MsgEntity) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(com.zl.chat.ui.chat.Constant.CONVERSATION_ITEM, msgEntity)
            activity.startActivity(intent)
        }

        fun startActivity(fragment: Fragment, msgEntity: MsgEntity) {
            val intent = Intent(fragment.context, ChatActivity::class.java)
            intent.putExtra(com.zl.chat.ui.chat.Constant.CONVERSATION_ITEM, msgEntity)
            fragment.startActivity(intent)
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ChatViewModel.Factory()).get(ChatViewModel::class.java)
    }

    override fun layoutId() = R.layout.activity_chat

    override fun initView(savedInstanceState: Bundle?) {

        mConversationInfo = intent.getParcelableExtra(com.zl.chat.ui.chat.Constant.CONVERSATION_ITEM)

        titleText.text = mConversationInfo.nickName

        mAdapter = ChatMsgAdapter(mList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }

    override fun setListener() {
        super.setListener()

        sendButton.setOnClickListener {
            if (inputEdit.isEmpty()) {
                return@setOnClickListener
            }
            val msgText = inputEdit.toTextString()

            val task= object : SingleTextMessageTask() {

                var index: Int = 0

                //fixme 会调用两次 ，很奇怪
                override fun request(): Any {
                    val message = TextMessage().apply {
                        id = getId()
                        from = MainApp.instance.user?.id!!
                        to = mConversationInfo.conversationId
                        msg = msgText
                    }
                    index = addRecord(message, 2)
                    return message
                }

                override fun onTaskEnd(errType: Int, errCode: Int) {
                    val entity = mList[index]
                    if (entity.id == id) {
                        if (errType == 0) {
                            //成功
                            entity.status = 0
                        } else {
                            //失败
                            entity.status = 1
                        }
                        runOnUiThread {
                            mAdapter.notifyDataSetChanged()
                        }
                    }
                }

            }

            MarsServiceProxy.inst.send(task)
            inputEdit.clear()
        }
    }

    val handler = PushMessageHandler {

        if (it.cmdId == Constant.CID_RECEIVE_SINGLE_TEXT_MSG) {
            val msg = it.toMessage(TextMessage::class.java)
            if (msg.from == MainApp.instance.user?.id) {
                Log.i(TAG, "msg.from == MainApp.instance.user?.id")
                return@PushMessageHandler
            } else {
                runOnUiThread {
                    Log.i(TAG, "add 124: ")
                    addRecord(msg)
                }
            }
        }
    }

    private fun addRecord(msg: TextMessage, status: Int = 0): Int {
        Log.i(TAG, "addRecord: ")
        val entity = ChatMsgEntity().apply {
            id = msg.id
            userId = msg.from
            message = msg.msg
        }
        entity.status = status
        mList.add(entity)
        mAdapter.notifyItemInserted(mList.size - 1)
        recyclerView.smoothScrollToPosition(mList.size - 1)
        return mList.size - 1
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