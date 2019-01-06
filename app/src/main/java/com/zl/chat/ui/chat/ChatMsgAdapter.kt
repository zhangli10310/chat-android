package com.zl.chat.ui.chat

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zl.core.base.BaseRecyclerViewAdapter

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/6 18:36.<br/>
 */
class ChatMsgAdapter(private var list: MutableList<String>): BaseRecyclerViewAdapter<String>(list) {

    override fun getView(parent: ViewGroup, viewType: Int): View {
        return TextView(parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as TextView).text = list[position]
    }
}