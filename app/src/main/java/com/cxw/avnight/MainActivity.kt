package com.cxw.avnight


import androidx.lifecycle.Observer
import com.cxw.avnight.base.BaseVMActivity

class MainActivity : BaseVMActivity<LouFengViewModel>() {
    override fun providerVMClass(): Class<LouFengViewModel>? = LouFengViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {
     //   mViewModel.getActorInfo(1,20)
        // mViewModel.getNavigation()
    }

    override fun initData() {

    }

    override fun startObserve() {
        super.startObserve()
//        mViewModel.mActorInfo.observe(this@MainActivity, Observer {
//            println(it)
//        })


    }


}
