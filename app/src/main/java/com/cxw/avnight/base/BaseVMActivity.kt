@file:Suppress("DEPRECATION")

package com.cxw.avnight.base

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity(), LifecycleObserver {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserve()
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    open fun providerVMClass(): Class<VM>? = null


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
    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}