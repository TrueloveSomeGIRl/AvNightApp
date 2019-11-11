package com.cxw.avnight.util

import android.content.Context
import android.content.Intent


import android.net.Uri

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import id.zelory.compressor.Compressor
import java.util.regex.Pattern


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
     *  文件上传 Retrofit
     */
    fun filesToMultipartBodyParts(context: Context, files: List<String>): List<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>(files.size)
        for (file in files) {
            val f = Compressor(context)
                .setMaxWidth(1920)
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

    fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }

    /**
     *  复制文本
     */
    fun copyTextContent(context: Context, text: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipboardManager.setPrimaryClip(clipData)

    }


     fun checkEtIsNotEmpty(context: Context,content: String, showTip: String) {
        if (!content.isNotBlank()) {
            Toast.makeText(context, showTip, Toast.LENGTH_LONG)
                .show()
            return
        }
    }

    fun isEmail(email: String): Boolean {
        val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }

}
