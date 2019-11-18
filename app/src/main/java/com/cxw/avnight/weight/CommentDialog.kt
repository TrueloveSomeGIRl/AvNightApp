package com.cxw.avnight.weight

import android.app.Dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.cxw.avnight.R
import org.jetbrains.anko.toast


class CommentDialog(private val mCommentsHint: String, //点击发表，内容不为空时的回调
                    var mCommentListener: CommentListener)//提示文字
    : DialogFragment() {

    private var mCommentsEt: EditText? = null

    interface CommentListener {
        fun Comment(inputText: String)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!, R.style.CommentsDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val contentView = View.inflate(activity, R.layout.comment_dialog_layout, null)
        dialog.setContentView(contentView)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window
        val lp = window!!.attributes
        lp.gravity = Gravity.BOTTOM
        lp.alpha = 1f
        lp.dimAmount = 0.5f
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mCommentsEt = contentView.findViewById(R.id.dialog_comment_content)
        mCommentsEt!!.hint = mCommentsHint
        val publishTv = contentView.findViewById<TextView>(R.id.dialog_comment_send)
        mCommentsEt!!.addTextChangedListener(object : TextWatcher {
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

        publishTv.setOnClickListener {
            if (TextUtils.isEmpty(mCommentsEt!!.text.toString())) {
                context!!.toast("评论不能为空")
                return@setOnClickListener
            }
            mCommentListener.Comment(mCommentsEt!!.text.toString())
        }
        mCommentsEt!!.isFocusable = true
        mCommentsEt!!.isFocusableInTouchMode = true
        mCommentsEt!!.requestFocus()
        val mHandler = Handler()
        dialog.setOnDismissListener { mHandler.postDelayed({ hideSoftKeyBoard() }, 100) }
        return dialog
    }


    private fun hideSoftKeyBoard() {
        try {
            (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: NullPointerException) {

        }

    }


}
