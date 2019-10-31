package com.cxw.avnight.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

object DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度，单位为px
     *
     * @param context
     * @return 返回int类型的屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = manager.defaultDisplay
        val outMetrics = DisplayMetrics()
        defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕高度，单位为px
     *
     * @param context
     * @return 返回int类型的屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = manager.defaultDisplay
        val outMetrics = DisplayMetrics()
        defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

}
