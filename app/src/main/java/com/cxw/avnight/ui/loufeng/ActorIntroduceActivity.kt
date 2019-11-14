package com.cxw.avnight.ui.loufeng


import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cxw.avnight.viewmodel.CommentsModel
import com.cxw.avnight.R
import com.cxw.avnight.adapter.CommentsAdapter

import com.cxw.avnight.base.BaseVMActivity
import com.cxw.avnight.dialog.AlertDialog
import com.cxw.avnight.mode.bean.*
import com.cxw.avnight.util.AppConfigs

import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.util.DisplayUtil
import com.cxw.avnight.weight.GlideImageLoader
import com.jaeger.library.StatusBarUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_actor_introduce.*
import org.jetbrains.anko.toast


class ActorIntroduceActivity : BaseVMActivity<CommentsModel>() {
    override fun providerVMClass(): Class<CommentsModel> = CommentsModel::class.java
    private var startPage = 1
    private var pageSize = 10

    companion object {
        const val KEY = "key"
    }
    private val commentsAdapter by lazy { CommentsAdapter() }

    private val QQURL: String = "mqqwpa://im/chat?chat_type=wpa&uin="
    private val imgUrlList = arrayListOf<String>()
    override fun getLayoutResId(): Int = R.layout.activity_actor_introduce




    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, top_bar_layout)
        setActorInfo()
        back_iv.setOnClickListener { finish() }
        feed_back_iv.setOnClickListener {

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.report -> {
                this.finish()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.actor_detial_menu, menu)
        return true
    }


    private fun setActorInfo() {
        val actorInfo = intent.getParcelableExtra<ActorInfo>(KEY)
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
                toast(getString(R.string.copy))
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
        with(actor_evaluation_tv){
            visibility = if (actorInfo.actor_evaluate.isEmpty()) View.GONE else View.VISIBLE
            actor_evaluation_tv.text = "上课评价:\n\n${actorInfo.actor_evaluate}"
        }
        with(actor_introduction_tv){
            visibility = if (actorInfo.actor_introduce.isEmpty()) View.GONE else View.VISIBLE
            actor_introduction_tv.text = "上课内容:\n\n${actorInfo.actor_introduce}"
        }

    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.mComments.observe(this@ActorIntroduceActivity, Observer {
            initComments(it)
        })
    }

    private fun initComments(it: Result<Comments>) {
        if (it.data.isNotEmpty()) {
            comment_layout.visibility = View.VISIBLE
            click_see_all.text = getString(R.string.click_more_comment)
            Glide.with(this@ActorIntroduceActivity).load(it.data[0].from_avatar)
                .into(comment_user_head_img_iv)
            comment_user_name_tv.text = it.data[0].from_name
            comment_content_tv.text = it.data[0].content
            comment_time_tv.text = it.data[0].create_time
            commentsAdapter.addData(it.data)
        }
    }

    override fun initData() {
        val dialogHeight = DisplayUtil.getScreenHeight(this) * 0.7
        click_see_all.setOnClickListener {
            val commentsDialog = AlertDialog.Builder(this@ActorIntroduceActivity, R.style.dialog1)
                .setContentView(R.layout.dialog_comments_layout)
                .setWidthAndHeight(
                    DisplayUtil.getScreenWidth(this), dialogHeight.toInt()
                )
                .formBottom(true)
                .show()
            val commentsRv = commentsDialog.getView<RecyclerView>(R.id.rv)
            commentsRv?.layoutManager = LinearLayoutManager(this)
            commentsRv?.adapter = commentsAdapter

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

}
