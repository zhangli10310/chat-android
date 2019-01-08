package com.zl.chat.ui.main.msg

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zl.chat.R
import com.zl.chat.ui.chat.ChatActivity
import com.zl.chat.ui.chat.Constant
import com.zl.core.base.ViewModelFragment
import kotlinx.android.synthetic.main.fragment_msg.*

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
class MsgFragment : ViewModelFragment<MsgViewModel>() {

    private var mList = mutableListOf<MsgEntity>()
    private lateinit var mAdapter: MsgAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, MsgViewModel.Factory()).get(MsgViewModel::class.java)
    }

    override fun layoutId() = R.layout.fragment_msg

    override fun initView(savedInstanceState: Bundle?) {

        recyclerView.layoutManager = LinearLayoutManager(activity)

        mAdapter = MsgAdapter(mList) {
            val position = it.adapterPosition
            if (position >= 0) {
                ChatActivity.startActivity(this, mList[position])
            }
        }
        recyclerView.adapter = mAdapter
    }

    override fun afterView() {
        super.afterView()

        loadData()
    }

    private fun loadData() {
        mList.add(MsgEntity().apply {
            nickName = "世界"
            conversationId = "all"
        })
        mAdapter.notifyDataSetChanged()
    }
}