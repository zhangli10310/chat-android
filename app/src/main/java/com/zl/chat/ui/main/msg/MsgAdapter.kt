package com.zl.chat.ui.main.msg

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zl.chat.R
import com.zl.core.base.BaseRecyclerViewAdapter
import com.zl.core.extend.inflate
import kotlinx.android.synthetic.main.item_friend_msg.view.*

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/30 19:10.<br/>
 */
class MsgAdapter(private val list: MutableList<MsgEntity>, private val onItemClick: ((RecyclerView.ViewHolder)->Unit)? = null) : BaseRecyclerViewAdapter<MsgEntity>(list) {

    override fun getView(parent: ViewGroup, viewType: Int): View {
        return parent.inflate(R.layout.item_friend_msg, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = list[position]

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(holder)
        }

        holder.itemView.nameText.text = entity.nickName
    }

}