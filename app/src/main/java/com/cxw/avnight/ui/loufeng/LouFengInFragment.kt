package com.cxw.avnight.ui.loufeng

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cxw.avnight.viewmodel.LouFengInViewModel
import com.cxw.avnight.R

import com.cxw.avnight.adapter.LouFengAdapter
import com.cxw.avnight.base.BaseVMFragment

import kotlinx.android.synthetic.main.loufeng_in_fragment.*


class LouFengInFragment : BaseVMFragment<LouFengInViewModel>() {
    override fun onNetReload(v: View) {

    }

    private val id by lazy { arguments?.getInt(index) }
    override fun providerVMClass(): Class<LouFengInViewModel>? = LouFengInViewModel::class.java
    private val louFengAdapter by lazy { LouFengAdapter() }
    private var verification = 1
    override fun getLayoutResId(): Int = R.layout.loufeng_in_fragment

    override fun initView() {
        rv.run {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = louFengAdapter
        }

        initAdapter()
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


    override fun initData() {
        mViewModel.getActorInfo(1, 20)
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        mBaseLoadService.showSuccess()
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
                louFengAdapter.addData(it)
            })
        }
    }
}