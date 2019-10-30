@file:Suppress("DEPRECATION")

package com.cxw.avnight.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()


}