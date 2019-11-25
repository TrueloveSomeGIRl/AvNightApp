package com.cxw.avnight.ui


import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baidu.mobstat.StatService
import com.bumptech.glide.Glide
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.ui.loufeng.LouFengFragment
import com.cxw.avnight.ui.upload.UploadActorFragment
import com.cxw.avnight.util.*
import com.cxw.avnight.viewmodel.MainViewModel
import com.jaeger.library.StatusBarUtil
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.util.AlbumUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.*
import java.io.File


class MainActivity : BaseVMActivity<MainViewModel>(), RadioGroup.OnCheckedChangeListener {
    private val appPath = Environment.getExternalStorageDirectory().toString() + File.separator

    override fun providerVMClass(): Class<MainViewModel> = MainViewModel::class.java
    private lateinit var mCurrentFragment: Fragment
    private lateinit var updateAppDialog: AlertDialog
    private lateinit var progressBar: ProgressBar
    override fun getLayoutResId(): Int = R.layout.activity_main
    private val QQURL: String = "mqqwpa://im/chat?chat_type=wpa&uin="
    override fun initView() {
        StatService.setUserId(this, SPUtil.getInt("userId", 1).toString())
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
        nav_view.getHeaderView(0).nav_header_name_tv.text =
                SPUtil.getString("userName", getString(R.string.app_name))
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_lw_vpn -> browse("https://www.lanzous.com/i7hibud")
                R.id.nav_potato -> browse("https://www.lanzous.com/i7hmdze")
                R.id.nav_login_out -> mViewModel.loginOut(SPUtil.getString("token"))
                R.id.nav_reward -> startActivity<RewardActivity>()
                R.id.nav_info_feedback -> {
                    if (BaseTools.isApplicationAvilible(
                                    this@MainActivity,
                                    "com.tencent.mobileqq"
                            )
                    )
                        startActivity(
                                Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(QQURL + AppConfigs.AUTHOR_QQ)
                                )
                        ) else

                        toast(getString(R.string.install_qq))
                }
            }
            false
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.lf_rb -> setTitleAndFragment(
                    resources.getString(R.string.lou_feng),
                    LouFengFragment::class.java
            )
            R.id.upload_rb -> setTitleAndFragment(
                    resources.getString(R.string.upload),
                    UploadActorFragment::class.java
            )
        }
    }

    private fun setTitleAndFragment(title: String, clazz: Class<*>) {
        main_top_title.text = title
        switchFragment(clazz)
    }

    private fun initFragment() {
        mCurrentFragment =
                FragmentMangerWrapper.instance.createFragment(LouFengFragment::class.java)
        supportFragmentManager.beginTransaction().add(R.id.man_container_layout, mCurrentFragment)
                .commit()
    }


    private fun switchFragment(clazz: Class<*>) {
        val fragment = FragmentMangerWrapper.instance.createFragment(clazz)
        if (fragment.isAdded) {
            supportFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment)
                    .commitAllowingStateLoss()
        } else {
            supportFragmentManager.beginTransaction().hide(mCurrentFragment)
                    .add(R.id.man_container_layout, fragment)
                    .commitAllowingStateLoss()
        }
        mCurrentFragment = fragment
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.mUpdateApp.observe(this, Observer { UpdateAppData ->
            if (BaseTools.getVersionCode(this) >= UpdateAppData.newVersion) {
                updateAppDialog = AlertDialog.Builder(this@MainActivity)
                        .setContentView(R.layout.updata_app_dialog_layout)
                        .setOnClickListener(R.id.canael_tv, View.OnClickListener {
                            updateAppDialog.cancel()
                        })
                        .setOnClickListener(R.id.update_tv, View.OnClickListener {
                            OkDownload.instance.download(
                                    UpdateAppData.apkUrl,
                                    object : OkDownload.OnDownloadListener {
                                        override fun onDownloadSuccess() {
                                            runOnUiThread {
                                                progressBar.visibility = View.GONE
                                            }
                                            BaseTools.installApk(appPath.plus("zfl.apk"))
                                            updateAppDialog.dismiss()
                                        }

                                        override fun onDownloading(progress: Int) {
                                            runOnUiThread {
                                                progressBar.visibility = View.VISIBLE
                                                progressBar.progress = progress
                                            }
                                        }

                                        override fun onDownloadFailed() {
                                            toast(getString(R.string.download_failed))
                                        }

                                    })
                        })
                        .fullWidth()
                        .setCancelable(false)
                        .show()
                progressBar = updateAppDialog.getView(R.id.update_progress_pb)!!
                val updateContent = updateAppDialog.getView<TextView>(R.id.update_content_tv)
                updateContent?.text = UpdateAppData.updateDescription
            }

        })
        mViewModel.loginOut.observe(this, Observer {
            //进了这里面必定成功了 的 所以不用判断 什么  状态码  直接清楚一些信息就行
            SPUtil.clear()
            startActivity(intentFor<SplashActivity>().clearTask().newTask())
        })
    }


}
