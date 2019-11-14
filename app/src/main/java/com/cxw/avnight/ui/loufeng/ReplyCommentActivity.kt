package com.cxw.avnight.ui.loufeng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cxw.avnight.R
import org.jetbrains.anko.toast

class ReplyCommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply_comment)
        val pos = intent.extras!!.getInt("replyCommentPosition")
       Log.d("cxx","$pos")
    }
}
