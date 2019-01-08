package com.zl.chat.ui.chat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zl.chat.MainApp
import com.zl.chat.R
import com.zl.core.base.BaseRecyclerViewAdapter
import com.zl.core.extend.inflate
import kotlinx.android.synthetic.main.item_msg_myself.view.*
import kotlinx.android.synthetic.main.item_msg_other.view.*

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/6 18:36.<br/>
 */
class ChatMsgAdapter(private var list: MutableList<ChatMsgEntity>): BaseRecyclerViewAdapter<ChatMsgEntity>(list) {

    override fun getView(parent: ViewGroup, viewType: Int): View {
        return if (viewType == 0) {
            parent.inflate(R.layout.item_msg_myself, parent)
        } else {
            parent.inflate(R.layout.item_msg_other, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = list[position]

        if (list[position].userId == MainApp.instance.user?.id) {//是自己
            holder.itemView.myMessageText.text = entity.message
//            holder.itemView.myNameText.text = entity.userId
            if (entity.status == 2) {
                holder.itemView.statusImg.visibility = View.VISIBLE
                holder.itemView.statusImg.setImageResource(R.mipmap.ic_loading)
            } else if (entity.status == 1) {
                holder.itemView.statusImg.visibility = View.VISIBLE
                holder.itemView.statusImg.setImageResource(R.mipmap.ic_warn)
            } else {
                holder.itemView.statusImg.visibility = View.GONE
            }

        } else { //是他人
            holder.itemView.otherMessageText.text = entity.message
            holder.itemView.uNameText.text = entity.userId
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].userId == MainApp.instance.user?.id) {//是自己
            0
        } else { //是他人
            1
        }
    }
}