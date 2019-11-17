package com.cxw.avnight.ui.loufeng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cxw.avnight.R
import com.cxw.avnight.weight.CommentDialog


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val commentDialog = CommentDialog(this)
        commentDialog.show()
    }

}
