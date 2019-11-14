@file:Suppress("DEPRECATION")

package com.cxw.avnight.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


abstract class BaseVMFragment<VM : BaseViewModel> : androidx.fragment.app.Fragment() {

     lateinit var mViewModel: VM
    protected lateinit var mBaseLoadService: LoadService<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = View.inflate(activity, getLayoutResId(), null)
        mBaseLoadService = LoadSir.getDefault().register(inflate) { v -> onNetReload(v) }
        return mBaseLoadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initView()
        initData()
        startObserve()
    }

    protected abstract fun onNetReload(v: View)
    open fun startObserve() {
        mViewModel.let {
            it.mException.observe(this, Observer {
                //其实这里更具状态来判断那种错误
                onError(it)
            })
            it.mLoading.observe(this, Observer {
                requestLoading(it)
            })
            it.mRequestSuccess.observe(this, Observer {
                requestSuccess(it)
            })
        }
    }

    open fun requestSuccess(requestSuccess: Boolean) {}
    open fun onError(e: Throwable) {}
    open fun requestLoading(isLoading: Boolean) {}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }


}