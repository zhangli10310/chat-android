package com.zl.core.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2019/1/7 11:57.<br/>
 */
class ChatRecyclerView : RecyclerView {

    private val TAG = ChatRecyclerView::class.java.simpleName

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "onSizeChanged: ")
        try {
            if (adapter != null && adapter!!.itemCount > 0) {
                smoothScrollToPosition(adapter!!.itemCount - 1)
            }
        } catch (e: Exception) {

        }

    }
}