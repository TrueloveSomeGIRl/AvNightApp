package com.cxw.avnight



import android.media.MediaPlayer
import android.util.Log

import com.cxw.avnight.base.BaseVMActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URISyntaxException

class MainActivity : BaseVMActivity<LouFengViewModel>() {
    override fun providerVMClass(): Class<LouFengViewModel>? = LouFengViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_main
    var webSocketClient: WebSocketClient? = null
    override fun initView() {
        //   mViewModel.getActorInfo(1,20)
        // mViewModel.getNavigation()
         initBgMusic()
        lv.setOnClickListener {
          //  playBrushBgMusic()
          a()
        }






    }


    override fun initData() {

    }

    fun a() {

        try {
            webSocketClient =
                object : WebSocketClient(URI("ws://192.168.0.159:8081/websocket"), object : Draft_6455(){}, null, 100000) {
                    override fun onOpen(handshakedata: ServerHandshake) {
                        Log.d("cxw","fdsfsd")
                    }
                    override fun onMessage(message: String) {
                       Log.d("cxw","fdsfsd")
                    }
                    override fun onClose(code: Int, reason: String, remote: Boolean) {}
                    override fun onClosing(code: Int, reason: String?, remote: Boolean) {
                        super.onClosing(code, reason, remote)
                    }
                    override fun onError(ex: Exception) {}
                }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        webSocketClient!!.connect()
    //    webSocketClient!!.send("123saa")


    }

    val BgMusic by lazy { MediaPlayer() }
    fun pauseBrushBgMusic() {
        if (BgMusic == null) return
        if (BgMusic.isPlaying) {
            BgMusic.pause()
        }
    }

    fun playBrushBgMusic() {
        if (BgMusic == null) return
        if (!BgMusic.isPlaying) {
            BgMusic.start()
        }
    }

    fun initBgMusic() {
        BgMusic.setDataSource("http://dict.youdao.com/dictvoice?audio=ariner's")
        BgMusic.prepareAsync()
        BgMusic.setOnPreparedListener {
            it.start()
        }
    }


}
