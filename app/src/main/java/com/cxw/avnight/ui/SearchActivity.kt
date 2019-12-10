package com.cxw.avnight.ui


import com.cxw.avnight.R
import com.cxw.avnight.base.BaseVMActivity

import com.cxw.avnight.viewmodel.SearchModel

class SearchActivity : BaseVMActivity<SearchModel>() {
    override fun providerVMClass(): Class<SearchModel> = SearchModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_search

    override fun initView() {
       // StatusBarUtil.setLightMode(this)
    }

    override fun initData() {
    }


}
