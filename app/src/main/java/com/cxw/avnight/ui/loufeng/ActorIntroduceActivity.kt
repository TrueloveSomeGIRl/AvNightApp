package com.cxw.avnight.ui.loufeng


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxw.avnight.viewmodel.CommentsModel
import com.cxw.avnight.R
import com.cxw.avnight.adapter.CommentsAdapter

import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.mode.bean.*
import com.cxw.avnight.util.AppConfigs

import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.DisplayUtil
import com.cxw.avnight.util.SPUtil
import com.cxw.avnight.weight.GlideImageLoader
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_actor_introduce.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_comments_layout.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * 感觉这里 刷新加载写的忒TM  SB
 */
class ActorIntroduceActivity : BaseVMActivity<CommentsModel>(), BaseQuickAdapter.RequestLoadMoreListener {


    private var currentPage: Int = 0
    private var pageTotal: Int = 0
    override fun providerVMClass(): Class<CommentsModel> = CommentsModel::class.java
    private var startPage = 1
    private var pageSize = 10
    private var actorId = -1
    private val mReplyComments = HashMap<String, Any>()
    private lateinit var commentsDialog: AlertDialog
    private lateinit var lv: LottieAnimationView
    private var clickShowCommentDialog: Boolean = false   //点击是否显示评论(包含Recyclerview)dialog 还是评论dialog
    private var isSetNewData: Boolean = false  //是否设置新得数据
    private var p: Int = 0

    companion object {
        const val KEY = "key"
    }

    override fun requestLoading(isLoading: Boolean) {
        super.requestLoading(isLoading)

//        if (isFirstRequestNetwork){
//            Log.d("cxx", "$isFirstRequestNetwork  $lv")
//            BaseTools.initLottieAnim(lv, View.VISIBLE, true)
//        }

    }


    override fun requestSuccess(requestSuccess: Boolean) {
        super.requestSuccess(requestSuccess)
//        if (isFirstRequestNetwork) {
//            isFirstRequestNetwork = false
        // BaseTools.initLottieAnim(lv, View.GONE, false)
//
//        }

    }

    private val commentsAdapter by lazy { CommentsAdapter() }
    private val QQURL: String = "mqqwpa://im/chat?chat_type=wpa&uin="
    private val imgUrlList = arrayListOf<String>()
    private var list = ArrayList<Comments>()
    override fun getLayoutResId(): Int = R.layout.activity_actor_introduce

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, top_bar_layout)
        setActorInfo()
        back_iv.setOnClickListener { finish() }

    }

    override fun onLoadMoreRequested() {
        if (currentPage < pageTotal) {
            startPage++
            mViewModel.getComments(actorId, startPage, pageSize)
        } else if (currentPage == pageTotal) {
            commentsAdapter.loadMoreEnd()
        }
    }

    private fun setActorInfo() {
        val actorInfo = intent.getParcelableExtra<ActorInfo>(KEY)
        actorId = actorInfo.id
        actorInfo!!.actorImgs.forEach {
            imgUrlList.add(it.img_url)
        }
        mViewModel.getComments(actorInfo.id, startPage, pageSize)
        banner.run {
            setImageLoader(GlideImageLoader())
            setImages(imgUrlList)
            isAutoPlay(true)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setDelayTime(AppConfigs.DELAY_TIME)
            setIndicatorGravity(bottom)
            setOnBannerListener { }
            start()
        }
        actor_name_tv.text = actorInfo.actor_name
        actor_age_tv.text = actorInfo.actor_age.toString()
        actor_bust_tv.text = actorInfo.actor_bust
        actor_city_tv.text = actorInfo.actor_city
        actor_evaluation_tv.text = actorInfo.actor_evaluate
        actor_height_tv.text = actorInfo.actor_height.toString()
        actor_weight_tv.text = actorInfo.actor_weight.toString()
        with(actor_qq_tv) {
            visibility = if (actorInfo.actor_qq.isEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                if (BaseTools.isApplicationAvilible(
                        this@ActorIntroduceActivity,
                        "com.tencent.mobileqq"
                    )
                )
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(QQURL + actorInfo.actor_qq)
                        )
                    ) else

                    toast(getString(R.string.install_qq))
            }
            text = actorInfo.actor_qq.plus(getString(R.string.contact))
        }
        with(actor_wx_tv) {
            visibility = if (actorInfo.actor_wx.isEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                BaseTools.copyTextContent(this@ActorIntroduceActivity, actorInfo.actor_wx)
                toast(getString(R.string.copy_success))
            }
            text = actorInfo.actor_wx.plus(getString(R.string.copy))
        }
        with(actor_phone_tv) {
            visibility = if (actorInfo.actor_phone.isEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                BaseTools.callPhone(actorInfo.actor_phone, this@ActorIntroduceActivity)
            }
            text = actorInfo.actor_phone.plus(getString(R.string.contact))
        }
        with(actor_work_address_tv) {
            visibility = if (actorInfo.actor_workaddress.isEmpty()) View.GONE else View.VISIBLE
            text = actorInfo.actor_workaddress
        }
        with(actor_evaluation_tv) {
            visibility = if (actorInfo.actor_evaluate.isEmpty()) View.GONE else View.VISIBLE
            actor_evaluation_tv.text = getString(R.string.class_evaluation_).plus(actorInfo.actor_evaluate)
        }
        with(actor_introduction_tv) {
            visibility = if (actorInfo.actor_introduce.isEmpty()) View.GONE else View.VISIBLE
            actor_introduction_tv.text = getString(R.string.class_content_).plus(actorInfo.actor_introduce)
        }

        with(actor_potato_tv) {
            visibility = if (actorInfo.actor_potato.isEmpty()) View.GONE else View.VISIBLE
            actor_potato_tv.text = actorInfo.actor_potato
            setOnClickListener {
                BaseTools.copyTextContent(this@ActorIntroduceActivity, actorInfo.actor_wx)
                toast(getString(R.string.copy_success))
            }
            text = actorInfo.actor_wx.plus(getString(R.string.copy))
        }

    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.let {
            it.mComments.observe(this@ActorIntroduceActivity, Observer { Comments ->
                initComments(Comments)
            })


            it.mSaveReplyComments.observe(this@ActorIntroduceActivity, Observer {
                dismissAndRequestComments()
            })


            it.mActorComments.observe(this@ActorIntroduceActivity, Observer {
                clickShowCommentDialog = false
                dismissAndRequestComments()
            })
        }
    }

    private fun dismissAndRequestComments() {
        commentsDialog.dismiss()
        BaseTools.closeKeybord(this@ActorIntroduceActivity)
        if (actorId != -1) {
            mViewModel.getComments(actorId, startPage, pageSize)
            isSetNewData = true
        }
    }

    private fun initComments(it: Result<Comments>) {
        if (it.data.isNotEmpty()) {
            first_comment_layout.visibility = View.VISIBLE
            click_see_all.text = getString(R.string.click_more_comment)
            Glide.with(this@ActorIntroduceActivity).load(it.data[0].from_avatar)
                .into(comment_user_head_img_iv)
            comment_user_name_tv.text = it.data[0].from_name
            comment_content_tv.text = it.data[0].content
            comment_time_tv.text = it.data[0].create_time
            commentsAdapter.loadMoreComplete()
            currentPage = it.currentPage
            if (isSetNewData) {
                commentsAdapter.setNewData(it.data)
            } else {
                commentsAdapter.addData(it.data)
            }
            pageTotal = it.pageTotal
        } else {
            clickShowCommentDialog = true
            click_see_all.text = getString(R.string.click_comment)
        }
    }

    override fun initData() {
        val dialogHeight = DisplayUtil.getScreenHeight(this) * 0.7
        click_see_all.setOnClickListener {
            if (clickShowCommentDialog) {
                showCommentDialog(0, false)
            } else {
                val commentsDialog =
                    AlertDialog.Builder(this@ActorIntroduceActivity, R.style.dialog1)
                        .setContentView(R.layout.dialog_comments_layout)
                        .setWidthAndHeight(
                            DisplayUtil.getScreenWidth(this), dialogHeight.toInt()
                        )
                        .formBottom(true)
                        .show()
                lv = commentsDialog.getView(R.id.la)!!
                val commentActor = commentsDialog.getView<TextView>(R.id.comment_actor_tv)
                val commentsRv = commentsDialog.getView<RecyclerView>(R.id.rv)
                commentsRv?.layoutManager = LinearLayoutManager(this)
                commentsRv?.adapter = commentsAdapter
                commentActor?.setOnClickListener {
                    showCommentDialog(0, false)
                }
            }
            commentsAdapter.setOnItemChildClickListener { adapter, view, position ->
                p = position
                list = adapter.data as ArrayList<Comments>
                when (view.id) {
                    R.id.total_reply_comment_tv -> {
                        startActivity<ReplyCommentActivity>(
                            "replyCommentPosition" to list[position].id,
                            "user_head_iv_url" to list[position].from_avatar,
                            "user_name_tv" to list[position].from_name,
                            "comment_content" to list[position].content,
                            "create_time" to list[position].create_time,
                            "id" to list[position].id,  //评论id
                            "from_id" to list[position].from_id  //  别人评论他用的id
                        )


                    }
                    R.id.comment_iv -> {
                        showCommentDialog(position, true)
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }


    @SuppressLint("SimpleDateFormat")
    private fun showCommentDialog(pos: Int, isReply: Boolean) {
        commentsDialog =
            AlertDialog.Builder(this@ActorIntroduceActivity, R.style.CommentsDialog)
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
        publishTv!!.setOnClickListener {
            mReplyComments.clear()
            if (isReply) {
                mReplyComments["comment_id"] = list[pos].id
                mReplyComments["from_id"] = SPUtil.getInt("userId")
                mReplyComments["from_name"] = SPUtil.getString("userName")
                mReplyComments["from_avatar"] = SPUtil.getString("headImg")
                mReplyComments["to_id"] = list[pos].from_id
                mReplyComments["to_name"] = list[pos].from_name
                mReplyComments["to_avatar"] = list[pos].from_avatar
                mReplyComments["content"] = commentEt.text.toString()
                mReplyComments["create_time"] =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                mViewModel.saveReplyComment(
                    RequestBody.create(
                        MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(mReplyComments)
                    )
                )
            } else {
                if (actorId != -1) {
                    mReplyComments["type"] = 1
                    mReplyComments["owner_id"] = actorId
                    mReplyComments["from_id"] = SPUtil.getInt("userId")
                    mReplyComments["from_name"] = SPUtil.getString("userName")
                    mReplyComments["from_avatar"] = SPUtil.getString("headImg")
                    mReplyComments["content"] = commentEt.text.toString()
                    mReplyComments["create_time"] =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                    mViewModel.addActorComment(
                        RequestBody.create(
                            MediaType.parse("application/json;charset=UTF-8"),
                            Gson().toJson(mReplyComments)
                        )
                    )
                }
            }
        }
    }


}
