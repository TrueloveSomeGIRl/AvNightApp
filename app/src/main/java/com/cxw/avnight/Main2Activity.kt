package com.cxw.avnight


import android.annotation.SuppressLint
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cxw.avnight.base.BaseActivity
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.util.FragmentMangerWrapper
import kotlinx.android.synthetic.main.activity_actor_introduce.*
import kotlinx.android.synthetic.main.app_bar_main.*

class Main2Activity : BaseActivity(), RadioGroup.OnCheckedChangeListener {


    private lateinit var mCurrentFragment: Fragment

    override fun getLayoutResId(): Int = R.layout.activity_main2

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        initFragment()
        // StatusBarUtil.setColor(this, R.color.appThemeColor)
        bottom_bar_rb.setOnCheckedChangeListener(this)
    }

    override fun initData() {

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.lf_rb -> setTitleAndFragment(resources.getString(R.string.lou_feng), LouFengFragment::class.java)
            R.id.upload_rb -> setTitleAndFragment(resources.getString(R.string.upload), UploadActorFragment::class.java)
        }
    }

    private fun setTitleAndFragment(title: String, clazz: Class<*>) {
        main_top_title.text = title
        switchFragment(clazz)
    }

    private fun initFragment() {
        mCurrentFragment = FragmentMangerWrapper.instance.createFragment(LouFengFragment::class.java)
        supportFragmentManager.beginTransaction().add(R.id.man_container_layout, mCurrentFragment).commit()
    }




    private fun switchFragment(clazz: Class<*>) {
        val fragment = FragmentMangerWrapper.instance.createFragment(clazz)
        if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.man_container_layout, fragment).commitAllowingStateLoss()
        }
        mCurrentFragment = fragment
    }

}
