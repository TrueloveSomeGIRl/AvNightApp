package com.cxw.avnight.util

import android.os.Environment
import androidx.annotation.NonNull
import okhttp3.*

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class OkDownload private constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient()

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun download(url: String, saveDir: String, listener: OnDownloadListener) {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener.onDownloadFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                var inputStream = response.body()!!.byteStream()
                val total = response.body()!!.contentLength()
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null
                // 储存下载文件的目录
                var sum: Long = 0
                val savePath = isExistDir(saveDir)
                val file = File(savePath, getNameFromUrl(url))
                try {
                    fos = FileOutputStream(file)
                    while (inputStream.read(buf).apply { len = this } > 0) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onDownloading(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener.onDownloadSuccess()
                } catch (e: Exception) {
                    listener.onDownloadFailed()
                } finally {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                    }
                    try {
                        fos?.close()
                    } catch (e: IOException) {
                    }

                }
            }
        })
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */

    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(Environment.getExternalStorageDirectory(), saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess()

        /**
         * @param progress 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }

    companion object {
        @Volatile
        private var mInstance: OkDownload? = null
        val instance: OkDownload
            @Synchronized get() {
                if (mInstance == null) {
                    synchronized(OkDownload::class.java) {
                        if (mInstance == null) {
                            mInstance = OkDownload()
                        }
                    }
                }
                return mInstance!!
            }
    }


}