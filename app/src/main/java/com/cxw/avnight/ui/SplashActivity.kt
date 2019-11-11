package com.cxw.avnight.ui


import android.content.Intent
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseActivity

import com.cxw.avnight.util.SPUtil
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*


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
                if (SPUtil.getBoolean("isLogin", false)) {
                    finish()
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }

        })

    }

}





