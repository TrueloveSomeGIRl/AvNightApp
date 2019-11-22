package com.cxw.avnight.ui


import android.widget.RadioGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseActivity
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.ui.loufeng.LouFengFragment
import com.cxw.avnight.ui.upload.UploadActorFragment
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.FragmentMangerWrapper
import com.cxw.avnight.util.SPUtil
import com.cxw.avnight.viewmodel.LoginViewModel
import com.cxw.avnight.viewmodel.MainViewModel
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.browse

class MainActivity : BaseVMActivity<MainViewModel>(), RadioGroup.OnCheckedChangeListener {

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java
    private lateinit var mCurrentFragment: Fragment

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, man_top_layout)
        StatusBarUtil.setLightMode(this)
        initFragment()
        checkUpdateApp()
        bottom_bar_rb.setOnCheckedChangeListener(this)
    }

    private fun checkUpdateApp() {
        mViewModel.checkUpdateApp()
    }

    override fun initData() {
        with(civ) {
            Glide.with(this).load(SPUtil.getString("headImg")).into(this)
            setOnClickListener {
                if (!drawer_layout.isDrawerOpen(GravityCompat.START))
                    drawer_layout.openDrawer(GravityCompat.START)
                else drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        nav_view.getHeaderView(0).nav_header_name_tv.text = SPUtil.getString("userName", getString(R.string.app_name))
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_lw_vpn -> browse("https://www.lanzous.com/i7hibud")
                R.id.nav_potato -> browse("https://www.lanzous.com/i7hmdze")
            }
            false
        }
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
            supportFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.man_container_layout, fragment)
                .commitAllowingStateLoss()
        }
        mCurrentFragment = fragment
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.mUpdateApp.observe(this, Observer {
            if (BaseTools.getVersionCode(this)<=it.newVersion){

            }
        })
    }

}
