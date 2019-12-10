package com.cxw.avnight.ui

import android.view.View
import com.cxw.avnight.R
import com.cxw.avnight.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_reward.*


class RewardActivity : BaseActivity() {


    override fun getLayoutResId(): Int = R.layout.activity_reward

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, top_bar_layout)
        StatusBarUtil.setLightMode(this)
        back_finish_iv.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }


}
