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


abstract class BaseLazyVMFragment<VM : BaseViewModel> : androidx.fragment.app.Fragment() {
    private var isViewInitiated: Boolean = false
    private var isVisibleToUser: Boolean = false
    private var isDataInitiated: Boolean = false
    protected lateinit var mViewModel: VM
    protected lateinit var mBaseLoadService: LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = View.inflate(activity, getLayoutResId(), null)
        mBaseLoadService = LoadSir.getDefault().register(inflate) { v -> onNetReload(v) }
        return mBaseLoadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initView()
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

    protected abstract fun onNetReload(v: View)
    open fun startObserve() {
        mViewModel.mException.observe(this, Observer {
            mBaseLoadService.showCallback(ErrorCallback::class.java)   //这里先这么解决 因为没有UI 没有好的错误处理方式 全返回404错误
            Toast.makeText(context, it.code, Toast.LENGTH_LONG).show()
        })
    }

 //   open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

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