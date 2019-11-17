package com.cxw.avnight.ui.loufeng

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxw.avnight.R
import com.cxw.avnight.adapter.CommentsAdapter
import com.cxw.avnight.adapter.ReplyByIdCommentAdapter

import com.cxw.avnight.base.BaseVMActivity

import com.cxw.avnight.viewmodel.ReplyCommentsModel
import kotlinx.android.synthetic.main.activity_reply_comment.*


class ReplyCommentActivity : BaseVMActivity<ReplyCommentsModel>() {
    override fun providerVMClass(): Class<ReplyCommentsModel> = ReplyCommentsModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_reply_comment
    private var startPage = 1
    private var pageSize = 10
    private val replyByIdCommentAdapter by lazy { ReplyByIdCommentAdapter() }
    override fun initView() {
        reply_comment_rv.layoutManager = LinearLayoutManager(this)
        reply_comment_rv.adapter = replyByIdCommentAdapter

    }

    override fun initData() {
        val replyCommentPos = intent.extras!!.getInt("replyCommentPosition")
        mViewModel.getSelectByIdReply(replyCommentPos, startPage, pageSize)
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.mReplyComments.observe(this, Observer {
            replyByIdCommentAdapter.addData(it.data)
        })
    }

}
