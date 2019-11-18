package com.cxw.avnight.ui.loufeng


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

import com.cxw.avnight.R
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.DisplayUtil
import com.cxw.avnight.weight.CommentDialog

import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        bt.setOnClickListener {
            val commentsDialog = AlertDialog.Builder(this@Main2Activity, R.style.CommentsDialog)
                    .setContentView(R.layout.comment_dialog_layout)
                    .fullWidth()
                    .formBottom(true)
                    .show()

            val commentEt = commentsDialog.getView<EditText>(R.id.dialog_comment_content_et)!!
            val publishTv = commentsDialog.getView<TextView>(R.id.dialog_comment_publish_tv)!!
            commentEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        publishTv.setBackgroundResource(R.drawable.corners_review_cansend)
                    } else {
                        publishTv.setBackgroundResource(R.drawable.corners_review_send)
                    }

                }
            })
            BaseTools.showSoftKeyboard(commentEt, this)
        }
    }


}
