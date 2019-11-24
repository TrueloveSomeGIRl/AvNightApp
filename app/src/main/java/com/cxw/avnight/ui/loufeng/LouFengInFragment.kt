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
import com.baidu.mobstat.StatService
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxw.avnight.viewmodel.LouFengInViewModel
import com.cxw.avnight.R

import com.cxw.avnight.adapter.LouFengAdapter
import com.cxw.avnight.base.BaseLazyVMFragment
import com.cxw.avnight.state.EmptyCallback
import com.cxw.avnight.util.AppConfigs
import com.cxw.avnight.weight.CustomLoadMoreView

import kotlinx.android.synthetic.main.loufeng_in_fragment.*

/**
 *   这里还没有完善 先写到这里  有些逻辑 没理清  以后在该
 */
class LouFengInFragment : BaseLazyVMFragment<LouFengInViewModel>(),
    BaseQuickAdapter.RequestLoadMoreListener {
    override fun fetchData() {

        // id?.let { mViewModel.getActorInfo(it, startPage, pageSize) }
        mViewModel.getActorInfo(id!!, startPage, pageSize)
    }

    private var isRefresh: Boolean = false
    private val id by lazy { arguments?.getInt(index) }
    override fun providerVMClass(): Class<LouFengInViewModel> = LouFengInViewModel::class.java
    private val louFengAdapter by lazy { LouFengAdapter() }
    private var startPage = 1
    private var pageSize = 10
    private var prePage = 0
    private var currentPage: Int = 0
    private var pageTotal: Int = 0
    override fun getLayoutResId(): Int = R.layout.loufeng_in_fragment


    override fun initView() {
        srl.setDistanceToTriggerSync(200)
        StatService.enableListTrack(rv)
        rv.run {
            if (id == 0)
                StatService.setListName(this, context.getString(R.string.thunder_teacher_rv))
            else
                StatService.setListName(this, context.getString(R.string.teacher_rv))

            layoutManager = StaggeredGridLayoutManager(
                AppConfigs.SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL
            )
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

        srl.setOnRefreshListener {
            isRefresh = true
            louFengAdapter.setEnableLoadMore(false)
            startPage = 1
            pageSize = 10
            mViewModel.getActorInfo(id!!, startPage, pageSize)
        }
    }


    override fun onLoadMoreRequested() {
        isRefresh = false
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
            }

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
                srl.isRefreshing = false
                mBaseLoadService.showSuccess()
                louFengAdapter.loadMoreComplete()
                currentPage = it.currentPage
                if (isRefresh)
                    louFengAdapter.setNewData(it.data)
                else
                    louFengAdapter.addData(it.data)
                pageTotal = it.pageTotal
                if (it.total == 0) {
                    mBaseLoadService.showCallback(EmptyCallback::class.java)
                }
            })
        }
    }
}