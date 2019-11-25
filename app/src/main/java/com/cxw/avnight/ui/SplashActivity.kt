package com.cxw.avnight.ui


import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

import com.cxw.avnight.base.BaseActivity

import kotlinx.android.synthetic.main.activity_splash.*
import com.cxw.avnight.R
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.util.SPUtil
import org.jetbrains.anko.startActivity


class SplashActivity : BaseActivity() {
    override fun initData() {

    }

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView() {
        val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.launch_bg_scale)
        launch_iv.startAnimation(loadAnimation)
        loadAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation) {
                val alertDialog = AlertDialog.Builder(this@SplashActivity)
                        .setContentView(R.layout.zfl_tip_layout)
                        .fullWidth()
                        .setCancelable(false)
                        .show()
                val sureTv = alertDialog.getView<TextView>(R.id.sure_tv)
                sureTv!!.setOnClickListener {
                    if (SPUtil.getBoolean("isLogin", false)) {
                        startActivity<MainActivity>()
                    } else {
                        startActivity<LoginActivity>()
                    }
                    finish()
                }
            }
        })
    }
}





