package com.zl.core.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/30 19:12.<br/>
 */
abstract class BaseRecyclerViewAdapter<T>(private val list: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return object : RecyclerView.ViewHolder(getView(parent, viewType)) {

        }
    }

    abstract fun getView(parent: ViewGroup, viewType: Int): View

    override fun getItemCount() = list.size
}