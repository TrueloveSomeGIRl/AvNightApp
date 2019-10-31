package com.cxw.avnight.util

import android.content.Context
import android.content.Intent


import android.net.Uri

import android.content.ClipData
import android.content.ClipboardManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList
import android.graphics.Bitmap
import android.os.Environment
import id.zelory.compressor.Compressor


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
            with(context.packageManager.getInstalledPackages(0)) {
                for (i in this.indices) {
                    val pn = this[i].packageName
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

    /**
     *  拨打电话
     */
    fun callPhone(phoneNum: String, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL)
        val parse = Uri.parse("tel:$phoneNum")
        intent.data = parse
        context.startActivity(intent)
    }

    /**
     *  复制文本
     */
    fun copyTextContent(context: Context, content: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", content)
        cm.primaryClip = mClipData
    }

    /**
     *  文件上传 Retrofit
     */
    fun filesToMultipartBodyParts(context: Context, files: List<String>): List<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>(files.size)
        for (file in files) {
            val f = Compressor(context)
                .setMaxWidth(720)
                .setMaxHeight(1080)
                .setQuality(80)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).absolutePath
                )
                .compressToFile(File(file))
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f)
            val part = MultipartBody.Part.createFormData("f", f.name, requestBody)
            parts.add(part)
        }
        return parts
    }


}
