package com.cxw.avnight.dialog

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import java.lang.ref.WeakReference

/**
 * Description:  Dialog  View的辅助处理类
 */
internal class DialogViewHelper() {

    /**
     * 获取ContentView
     *
     * @return
     */
    /**
     * 设置布局View
     *
     * @param contentView
     */
    var contentView: View? = null
    // 防止霸气侧漏
    private val mViews: SparseArray<WeakReference<View>> = SparseArray()

    constructor(context: Context, layoutResId: Int) : this() {
        contentView = LayoutInflater.from(context).inflate(layoutResId, null)
    }


    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    fun setText(viewId: Int, text: CharSequence) {
        // 每次都 findViewById   减少findViewById的次数
        val tv = getView<TextView>(viewId)
        if (tv != null) {
            tv.text = text
        }
    }

    fun <T : View> getView(viewId: Int): T? {
        val viewReference = mViews.get(viewId)
        // 侧漏的问题  到时候到这个系统优化的时候再去介绍
        var view: View? = null
        if (viewReference != null) {
            view = viewReference.get()
        }

        if (view == null) {
            view = contentView!!.findViewById(viewId)
            if (view != null) {
                mViews.put(viewId, WeakReference(view))
            }
        }
        return view as T?
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    fun setOnclickListener(viewId: Int, listener: View.OnClickListener) {
        val view = getView<View>(viewId)
        view?.setOnClickListener(listener)
    }
}
