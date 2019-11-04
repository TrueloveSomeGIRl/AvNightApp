package com.cxw.avnight.ui



import com.cxw.avnight.R
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.viewmodel.LouFengViewModel

class SplashActivity : BaseVMActivity<LouFengViewModel>() {
    override fun providerVMClass(): Class<LouFengViewModel>? = LouFengViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView() {

    }


    override fun initData() {

    }

}
