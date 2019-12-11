package com.cxw.avnight.ui


import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxw.avnight.R

import com.cxw.avnight.adapter.SearchAdapter
import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.ui.loufeng.ActorIntroduceActivity
import com.cxw.avnight.viewmodel.SearchModel
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity


class SearchActivity : BaseVMActivity<SearchModel>() , BaseQuickAdapter.RequestLoadMoreListener{
    override fun onLoadMoreRequested() {
        isRefresh = false
        if (currentPage < pageTotal) {
            startPage++
            mViewModel.searchActor(search_et.text.toString().trim(), startPage, pageSize)
        } else if (currentPage == pageTotal) {
            searchAdapter.loadMoreEnd()
        }

    }

    private var isRefresh: Boolean = false
    override fun providerVMClass(): Class<SearchModel> = SearchModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_search
    private val searchAdapter by lazy { SearchAdapter() }
    private var startPage = 1
    private var pageSize = 10
    private var currentPage: Int = 0
    private var pageTotal: Int = 0
    override fun initView() {
        StatusBarUtil.setLightMode(this)
        search_rv.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }
    }

    override fun initData() {
        search_iv.setOnClickListener {
            mViewModel.searchActor(search_et.text.toString().trim(), startPage, pageSize)
        }
        with(searchAdapter) {
            setOnItemClickListener { _, _, position ->
                startActivity<ActorIntroduceActivity>(ActorIntroduceActivity.KEY to data[position])
            }
        }
    }

    override fun requestLoading(isLoading: Boolean) {
        super.requestLoading(isLoading)

    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.mSearchActorInfo.observe(this, Observer {
            searchAdapter.addData(it.data)
        })
    }

}
