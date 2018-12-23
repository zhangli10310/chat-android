package com.zl.core.base

import android.view.Gravity
import androidx.fragment.app.Fragment

/**
 * Created by zhangli on 2018/12/22,21:49<br/>
 */
open class BaseFragment : Fragment() {


    fun showToastSafe(text: CharSequence, gravity: Int = Gravity.BOTTOM, xOffset: Int = 0, yOffset: Int = 0) {
        (activity as? BaseActivity)?.showToastSafe(text, gravity, xOffset, yOffset)
    }
}