@file:Suppress("DEPRECATION")

package com.cxw.avnight.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {
    lateinit var mViewModel: VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        startObserve()
    }


    open fun startObserve() {
        mViewModel.let {
            it.mException.observe(viewLifecycleOwner, Observer {
                //其实这里更具状态来判断那种错误
                onError(it)
            })
            it.mLoading.observe(viewLifecycleOwner, Observer {
                requestLoading(it)
            })
            it.mRequestSuccess.observe(viewLifecycleOwner, Observer {
                requestSuccess(it)
            })
        }
    }

    open fun requestSuccess(requestSuccess: Boolean) {}
    open fun onError(e: Throwable) {}
    open fun requestLoading(isLoading: Boolean) {}

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