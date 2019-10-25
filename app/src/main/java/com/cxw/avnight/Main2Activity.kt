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


    private val fragmentList = arrayListOf<Fragment>()
    private val LouFengFragment by lazy { LouFengFragment() } // 楼凤
    private val UploadFragment by lazy { UploadActorFragment() } // 上传 演员
    override fun getLayoutResId(): Int = R.layout.activity_main2

    init {
        fragmentList.add(LouFengFragment)
        fragmentList.add(UploadFragment)
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        initFragment()
        // StatusBarUtil.setColor(this, R.color.appThemeColor)
        bottom_bar_rb.setOnCheckedChangeListener(this)
        main_top_title.setOnClickListener {
            AlertDialog.Builder(this@Main2Activity, R.style.dialog1)
                .setContentView(R.layout.dialog_comments_layout)
                .formBottom(true)
                .setCancelable(true)
                .fullWidth()
                .show()
        }
    }

    override fun initData() {

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.lf_rb -> setTitleAndFragment(resources.getString(R.string.lou_feng), LouFengFragment::class.java)
            R.id.upload_rb -> setTitleAndFragment(resources.getString(R.string.upload), UploadFragment::class.java)
        }
    }

    private fun setTitleAndFragment(title: String, clazz: Class<*>) {
        main_top_title.text = title
        switchFragment(clazz)
    }

    private lateinit var mCurrentFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager
    private fun initFragment() {
        mFragmentManager = supportFragmentManager
        mCurrentFragment = FragmentMangerWrapper.getInstance().createFragment(LouFengFragment::class.java)
        mFragmentManager.beginTransaction().add(R.id.man_container_layout, mCurrentFragment).commit()
    }

    private fun switchFragment(clazz: Class<*>) {
        val fragment = FragmentMangerWrapper.getInstance().createFragment(clazz)
        if (fragment.isAdded) {
            mCurrentFragment?.let {
                mFragmentManager.beginTransaction().hide(it).show(fragment).commitAllowingStateLoss()
            }
        } else {
            mCurrentFragment?.let {
                mFragmentManager.beginTransaction().hide(it).add(R.id.man_container_layout, fragment)
                    .commitAllowingStateLoss()
            }
        }
        mCurrentFragment = fragment
    }
}
