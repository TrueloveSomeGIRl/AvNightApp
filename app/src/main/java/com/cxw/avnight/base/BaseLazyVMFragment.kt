@file:Suppress("DEPRECATION")

package com.cxw.avnight.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cxw.avnight.state.EmptyCallback
import com.cxw.avnight.state.ErrorCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import org.jetbrains.anko.toast


abstract class BaseLazyVMFragment<VM : BaseViewModel> : BaseFragment() {
    private var isViewInitiated: Boolean = false
    private var isVisibleToUser: Boolean = false
    private var isDataInitiated: Boolean = false
    protected lateinit var mViewModel: VM



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        startObserve()
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    /**
     * 取来数据
     */
    abstract fun fetchData()

    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }



    open fun startObserve() {
        mViewModel.let {
            it.mException.observe(viewLifecycleOwner, Observer {
                //其实这里更具状态来判断那种错误
             //   onError(it)
                mBaseLoadService.showCallback(ErrorCallback::class.java)   //这里先这么解决 因为没有UI 没有好的错误处理方式 全返回错误
                context!!.toast(it.message.toString())
            })
            it.mLoading.observe(viewLifecycleOwner, Observer {
                requestLoading(it)
            })
            it.mRequestSuccess.observe(viewLifecycleOwner, Observer {
                requestSuccess(it)
            })
        }

    }

    open fun onError(e: Throwable) {}
    open fun requestSuccess(requestSuccess: Boolean) {}

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