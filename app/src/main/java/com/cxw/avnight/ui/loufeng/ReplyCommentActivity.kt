package com.cxw.avnight.ui.loufeng


import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxw.avnight.R

import com.cxw.avnight.adapter.ReplyByIdCommentAdapter

import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.Comments
import com.cxw.avnight.mode.bean.Result
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.SPUtil

import com.cxw.avnight.viewmodel.ReplyCommentsModel
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_reply_comment.*
import kotlinx.android.synthetic.main.item_reply_comments_layout.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 *   MMP  怎么越想越复杂  我难啊  以后有时间在简化吧
 */
class ReplyCommentActivity : BaseVMActivity<ReplyCommentsModel>(),
    BaseQuickAdapter.RequestLoadMoreListener {
    private var isRefresh: Boolean = false
    private val replyCommentPos by lazy { intent.extras!!.getInt("replyCommentPosition") }
    private val userName by lazy { intent.extras!!.getString("user_name_tv") }
    private val createTime by lazy { intent.extras!!.getString("create_time") }
    private val commentContent by lazy { intent.extras!!.getString("comment_content") }
    private val userHeadUrl by lazy { intent.extras!!.getString("user_head_iv_url") }

    companion object {
        var fromId: Int? = null
    }

    override fun onLoadMoreRequested() {
        isRefresh = false
        if (currentPage < pageTotal) {
            startPage++
            mViewModel.getSelectByIdReply(replyCommentPos, startPage, pageSize)
        } else if (currentPage == pageTotal) {
            replyByIdCommentAdapter.loadMoreEnd()
        }

    }

    private lateinit var mReplyComments: Result<ChildComment>
    private val mSaveReplyComments = HashMap<String, Any>()
    private lateinit var commentsDialog: AlertDialog
    private var startPage = 1
    private var pageSize = 10
    private var currentPage: Int = 0
    private var pageTotal: Int = 0
    override fun providerVMClass(): Class<ReplyCommentsModel> = ReplyCommentsModel::class.java
    override fun getLayoutResId(): Int = R.layout.activity_reply_comment

    private val replyByIdCommentAdapter by lazy { ReplyByIdCommentAdapter() }
    override fun initView() {
        fromId = intent.extras!!.getInt("from_id")

        StatusBarUtil.setTranslucentForImageView(this, 0, bar)
        StatusBarUtil.setLightMode(this)

        srl.setOnRefreshListener {
            isRefresh = true
            replyByIdCommentAdapter.setEnableLoadMore(false)
            startPage = 1
            pageSize = 10
            mViewModel.getSelectByIdReply(replyCommentPos, startPage, pageSize)
        }

        initRv()
        replyByIdCommentAdapter.setOnItemClickListener { _, _, position ->
            showCommentDialog(false, position)
        }
        replyByIdCommentAdapter.setOnItemChildClickListener { _, _, position ->
            showCommentDialog(false, position)
        }

        back_iv.setOnClickListener {
            finish()
        }
    }

    private fun initRv() {
        reply_comment_rv.layoutManager = LinearLayoutManager(this)
        reply_comment_rv.adapter = replyByIdCommentAdapter

        val view = layoutInflater.inflate(
            R.layout.reply_comment_detail_head_layout,
            reply_comment_rv.parent as ViewGroup,
            false
        )

        replyByIdCommentAdapter.addHeaderView(view)
        val commentIv = view.findViewById<ImageView>(R.id.comment_iv)
        commentIv.setOnClickListener {
            showCommentDialog(true, -1)
        }
        val name = view.findViewById<TextView>(R.id.reply_comment_user_name_tv)
        val headImg = view.findViewById<ImageView>(R.id.reply_comment_user_head_img_iv)
        val dateTv = view.findViewById<TextView>(R.id.reply_comment_time_tv)
        val comment = view.findViewById<TextView>(R.id.reply_comment_tv)
        name.text = userName
        dateTv.text = createTime
        comment.text = commentContent
        Glide.with(this).load(userHeadUrl)
            .into(headImg)

    }

    override fun initData() {
        mViewModel.getSelectByIdReply(replyCommentPos, startPage, pageSize)
    }

    private var isSetNewData: Boolean = false  //是否设置新得数据

    override fun startObserve() {
        super.startObserve()
        mViewModel.mReplyComments.observe(this, Observer {
            mReplyComments = it
            total_reply_comment_tv.text = it.data.size.toString().plus("条回复")
            srl.isRefreshing = false
            replyByIdCommentAdapter.loadMoreComplete()
            currentPage = it.currentPage
            if (isRefresh || isSetNewData)
                replyByIdCommentAdapter.setNewData(it.data)
            else
                replyByIdCommentAdapter.addData(it.data)
            pageTotal = it.pageTotal

        })

        mViewModel.mSaveReplyComments.observe(this, Observer {
            commentsDialog.dismiss()
            BaseTools.closeKeybord(this@ReplyCommentActivity)
            isSetNewData = true
            mViewModel.getSelectByIdReply(replyCommentPos, startPage, pageSize)
        })
    }


    @SuppressLint("SimpleDateFormat")
    private fun showCommentDialog(isHeadView: Boolean, pos: Int) {
        commentsDialog =
            AlertDialog.Builder(this@ReplyCommentActivity, R.style.CommentsDialog)
                .setContentView(R.layout.comment_dialog_layout)
                .fullWidth()
                .formBottom(true)
                .show()
        val commentEt = commentsDialog.getView<EditText>(R.id.dialog_comment_content_et)
        val publishTv = commentsDialog.getView<TextView>(R.id.dialog_comment_publish_tv)
        publishTv!!.isEnabled = false
        commentEt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    publishTv.isEnabled = true
                    publishTv.setBackgroundResource(R.drawable.corners_review_cansend)
                } else {
                    publishTv.isEnabled = false
                    publishTv.setBackgroundResource(R.drawable.corners_review_send)
                }
            }
        })
        BaseTools.showSoftKeyboard(commentEt, this)
        publishTv.setOnClickListener {
            mSaveReplyComments.clear()
            mSaveReplyComments["comment_id"] = intent.extras!!.getInt("id")
            mSaveReplyComments["from_id"] = SPUtil.getInt("userId")
            mSaveReplyComments["from_name"] = SPUtil.getString("userName")
            mSaveReplyComments["from_avatar"] = SPUtil.getString("headImg")
            mSaveReplyComments["to_id"] =
                if (isHeadView) fromId!! else mReplyComments.data[pos].from_id
            mSaveReplyComments["to_name"] =
                if (isHeadView) userName!! else mReplyComments.data[pos].from_name
            mSaveReplyComments["to_avatar"] =
                if (isHeadView) userHeadUrl!! else mReplyComments.data[pos].from_avatar
            mSaveReplyComments["content"] = commentEt.text.toString()
            mSaveReplyComments["create_time"] =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            mViewModel.saveReplyComment(
                RequestBody.create(
                    MediaType.parse("application/json;charset=UTF-8"),
                    Gson().toJson(mSaveReplyComments)
                )
            )
            Log.d("cxx", "${Gson().toJson(mSaveReplyComments)}")
        }
    }
}


