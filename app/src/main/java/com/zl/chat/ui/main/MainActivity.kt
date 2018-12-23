package com.zl.chat.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zl.chat.R
import com.zl.chat.ui.main.contact.ContactFragment
import com.zl.chat.ui.main.find.FindFragment
import com.zl.chat.ui.main.mine.MineFragment
import com.zl.chat.ui.main.msg.MsgFragment
import com.zl.core.base.BaseFragment
import com.zl.core.base.ViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 *<p></p>
 *
 * Created by zhangli on 2018/12/21 16:19.<br/>
 */
class MainActivity : ViewModelActivity<MainViewModel>() {

    private var mCurrentFragment: BaseFragment? = null
    private var msgFragment: MsgFragment? = null
    private var contactFragment: ContactFragment? = null
    private var findFragment: FindFragment? = null
    private var mineFragment: MineFragment? = null

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)
    }

    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun setListener() {
        super.setListener()
        msgIconImg.setOnClickListener {
            selectMsg()
        }

        contactIconImg.setOnClickListener {
            selectContact()
        }

        findIconImg.setOnClickListener {
            selectFind()
        }

        mineIconImg.setOnClickListener {
            selectMine()
        }
    }

    private fun selectMsg() {
        msgIconImg.setImageResource(R.mipmap.ic_msg_selected)
        contactIconImg.setImageResource(R.mipmap.ic_contact)
        findIconImg.setImageResource(R.mipmap.ic_find)
        mineIconImg.setImageResource(R.mipmap.ic_mine)

        if (msgFragment == null) {
            msgFragment = MsgFragment()
        }
        showFragment(msgFragment!!)
    }

    private fun selectContact() {
        msgIconImg.setImageResource(R.mipmap.ic_msg)
        contactIconImg.setImageResource(R.mipmap.ic_contact_selected)
        findIconImg.setImageResource(R.mipmap.ic_find)
        mineIconImg.setImageResource(R.mipmap.ic_mine)

        if (contactFragment == null) {
            contactFragment = ContactFragment()
        }

        showFragment(contactFragment!!)
    }

    private fun selectFind() {
        msgIconImg.setImageResource(R.mipmap.ic_msg)
        contactIconImg.setImageResource(R.mipmap.ic_contact)
        findIconImg.setImageResource(R.mipmap.ic_find_selected)
        mineIconImg.setImageResource(R.mipmap.ic_mine)

        if (findFragment == null) {
            findFragment = FindFragment()
        }
        showFragment(findFragment!!)
    }

    private fun selectMine() {
        msgIconImg.setImageResource(R.mipmap.ic_msg)
        contactIconImg.setImageResource(R.mipmap.ic_contact)
        findIconImg.setImageResource(R.mipmap.ic_find)
        mineIconImg.setImageResource(R.mipmap.ic_mine_selected)

        if (mineFragment == null) {
            mineFragment = MineFragment()
        }

        showFragment(mineFragment!!)
    }

    override fun afterView() {
        super.afterView()
        selectMsg()

        msgUnreadCountText.text = "12"
    }

    private fun showFragment(fragment: BaseFragment) {
        if (fragment != mCurrentFragment) {
            val transaction = supportFragmentManager.beginTransaction()

            val fragmentByTag = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
            if (fragmentByTag != null) {
                transaction.show(fragmentByTag)
            } else {
                transaction.add(R.id.frameLayout, fragment, fragment::class.java.simpleName)
            }

            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment!!)
            }

            transaction.commit()
            mCurrentFragment = fragment
        }
    }
}