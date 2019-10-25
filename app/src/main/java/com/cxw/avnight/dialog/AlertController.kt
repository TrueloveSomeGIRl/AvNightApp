package com.cxw.avnight.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.*


internal class AlertController(
    /**
     * 获取Dialog
     *
     * @return
     */
    val dialog: AlertDialog,
    /**
     * 获取Dialog的Window
     *
     * @return
     */
    val window: Window
) {

    private var mViewHelper: DialogViewHelper? = null

    fun setViewHelper(viewHelper: DialogViewHelper) {
        this.mViewHelper = viewHelper
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    fun setText(viewId: Int, text: CharSequence) {
        mViewHelper!!.setText(viewId, text)
    }

    fun <T : View> getView(viewId: Int): T? {
        return mViewHelper!!.getView(viewId)
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    fun setOnclickListener(viewId: Int, listener: View.OnClickListener) {
        mViewHelper!!.setOnclickListener(viewId, listener)
    }

    class AlertParams(var mContext: Context, var mThemeResId: Int) {
        // 点击空白是否能够取消  默认点击阴影可以取消
        var mCancelable = true
        // dialog Cancel监听
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        // dialog Dismiss监听
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        // dialog Key监听
        var mOnKeyListener: DialogInterface.OnKeyListener? = null
        // 布局View
        var mView: View? = null
        // 布局layout id
        var mViewLayoutResId: Int = 0
        // 存放字体的修改
        var mTextArray = SparseArray<CharSequence>()
        // 存放点击事件
        var mClickArray = SparseArray<View.OnClickListener>()
        // 宽度
        var mWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        // 动画
        var mAnimations = 0
        // 位置
        var mGravity = Gravity.CENTER
        // 高度
        var mHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        fun apply(mAlert: AlertController) {
            // 完善这个地方 设置参数

            // 1. 设置Dialog布局  DialogViewHelper
            var viewHelper: DialogViewHelper? = null
            if (mViewLayoutResId != 0) {
                viewHelper = DialogViewHelper(mContext, mViewLayoutResId)
            }

            if (mView != null) {
                viewHelper = DialogViewHelper()
                viewHelper.contentView = mView
            }

            if (viewHelper == null) {
                throw IllegalArgumentException("请设置布局setContentView()")
            }

            // 给Dialog 设置布局
            mAlert.dialog.setContentView(viewHelper.contentView!!)

            // 设置 Controller的辅助类
            mAlert.setViewHelper(viewHelper)

            // 2.设置文本
            val textArraySize = mTextArray.size()
            for (i in 0 until textArraySize) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i))
            }


            // 3.设置点击
            val clickArraySize = mClickArray.size()
            for (i in 0 until clickArraySize) {
                mAlert.setOnclickListener(mClickArray.keyAt(i), mClickArray.valueAt(i))
            }

            // 4.配置自定义的效果  全屏  从底部弹出    默认动画
            val window = mAlert.window
            // 设置位置
            window.setGravity(mGravity)

            // 设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations)
            }

            // 设置宽高
            val params = window.attributes
            params.width = mWidth
            params.height = mHeight
            window.attributes = params
        }
    }
}