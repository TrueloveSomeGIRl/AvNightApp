package com.cxw.avnight.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

object BaseTools {
    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    fun isApplicationAvilible(context: Context, appPackageName: String): Boolean {
        try {
            // 获取packagemanager
            val packageManager = context.packageManager
            // 获取所有已安装程序的包信息
            val pinfo = packageManager.getInstalledPackages(0)
            if (pinfo != null) {
                for (i in pinfo.indices) {
                    val pn = pinfo[i].packageName
                    if (appPackageName == pn) {
                        return true
                    }
                }
            }
            return false
        } catch (ignored: Exception) {
            return false
        }

    }
}
