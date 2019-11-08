package com.cxw.avnight.ui.loufeng

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxw.avnight.viewmodel.LouFengInViewModel
import com.cxw.avnight.R

import com.cxw.avnight.adapter.LouFengAdapter
import com.cxw.avnight.base.BaseLazyVMFragment
import com.cxw.avnight.state.EmptyCallback
import com.cxw.avnight.util.AppConfigs
import com.cxw.avnight.weight.CustomLoadMoreView

import kotlinx.android.synthetic.main.loufeng_in_fragment.*


class LouFengInFragment : BaseLazyVMFragment<LouFengInViewModel>(),
    BaseQuickAdapter.RequestLoadMoreListener {
    override fun fetchData() {
        //  这里一直没 怎么研究透 这个空判断  只是懂这个意思  不让抛 npe 就用let 感觉多了点代码
       id?.let { mViewModel.getActorInfo(it, startPage, pageSize) }
        //  mViewModel.getActorInfo(id!!, startPage, pageSize)
    }


    private val id by lazy { arguments?.getInt(index) }
    override fun providerVMClass(): Class<LouFengInViewModel>? = LouFengInViewModel::class.java
    private val louFengAdapter by lazy { LouFengAdapter() }
    private var startPage = 1
    private var pageSize = 10
    private var prePage = 0
    private var currentPage: Int = 0
    private var pageTotal: Int = 0
    override fun getLayoutResId(): Int = R.layout.loufeng_in_fragment

    override fun initView() {
        rv.run {
            layoutManager = StaggeredGridLayoutManager(AppConfigs.SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            (layoutManager as StaggeredGridLayoutManager).gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_NONE//防止item 交换位置
            (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            itemAnimator!!.changeDuration = 0
            adapter = louFengAdapter
            louFengAdapter.setOnLoadMoreListener(this@LouFengInFragment, this)
            louFengAdapter.setLoadMoreView(CustomLoadMoreView())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    (layoutManager as StaggeredGridLayoutManager).invalidateSpanAssignments()//防止第一行到顶部有空白
                }
            })

        }

        initAdapter()
    }


    override fun onLoadMoreRequested() {
        if (currentPage < pageTotal) {
            startPage++
            id?.let { mViewModel.getActorInfo(it, startPage, pageSize) }
        } else if (currentPage == pageTotal) {
            louFengAdapter.loadMoreEnd()
        }

    }

    override fun onNetReload(v: View) {
        id?.let { mViewModel.getActorInfo(it, startPage, pageSize) }
    }

    private fun initAdapter() {
        with(louFengAdapter) {
            setOnItemClickListener { _, _, position ->
                val intent = Intent(context, ActorIntroduceActivity::class.java)
                intent.putExtra(ActorIntroduceActivity.KEY, data[position])
                context?.startActivity(intent)
                println(data[position])
            }
            //其它
        }
    }



    companion object {
        private const val index = "INDEX"
        fun newInstance(id: Int): LouFengInFragment {
            val fragment = LouFengInFragment()
            val bundle = Bundle()
            bundle.putInt(index, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.run {
            mActorInfo.observe(this@LouFengInFragment, Observer {
                mBaseLoadService.showSuccess()
                louFengAdapter.loadMoreComplete()   //这里还有个条件没有判断  先放在这里面
                louFengAdapter.addData(it.data)
                currentPage = it.currentPage
                pageTotal = it.pageTotal
                if (it.total==0){
                    mBaseLoadService.showCallback(EmptyCallback::class.java)
                }
            })
        }
    }
}