package com.cxw.avnight.ui.loufeng


import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cxw.avnight.viewmodel.CommentsModel
import com.cxw.avnight.R

import com.cxw.avnight.base.BaseVMActivity

import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.util.BaseTools
import com.cxw.avnight.weight.GlideImageLoader
import com.jaeger.library.StatusBarUtil
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_actor_introduce.*


class ActorIntroduceActivity : BaseVMActivity<CommentsModel>() {
    override fun providerVMClass(): Class<CommentsModel>? = CommentsModel::class.java
    var startPage = 1
    var pageSize = 10

    companion object {
        const val KEY = "key"
    }

    private val QQURL: String = "mqqwpa://im/chat?chat_type=wpa&uin="
    private val imgUrlList = arrayListOf<String>()
    override fun getLayoutResId(): Int = R.layout.activity_actor_introduce

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, top_bar_layout)
        setActorInfo()
        back_iv.setOnClickListener { finish() }
    }

    private fun setActorInfo() {
        val info = intent.getParcelableExtra<ActorInfo>(KEY)

        info!!.actorImgs.forEach {
            imgUrlList.add(it.img_url)
        }

        mViewModel.getComments(info.id, startPage, pageSize)
        banner.run {
            setImageLoader(GlideImageLoader())
            setImages(imgUrlList)
            isAutoPlay(true)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setDelayTime(2000)
            setIndicatorGravity(bottom)
            setOnBannerListener { }
            start()
        }
        actor_name_tv.text = info.actor_name
        actor_age_tv.text = info.actor_age.toString()
        actor_bust_tv.text = info.actor_bust
        actor_city_tv.text = info.actor_city
        actor_evaluation_tv.text = info.actor_evaluate
        actor_height_tv.text = info.actor_height.toString()
        actor_weight_tv.text = info.actor_weight.toString()
        with(actor_qq_tv) {
            if (info.actor_qq.isEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                if (BaseTools.isApplicationAvilible(this@ActorIntroduceActivity, "com.tencent.mobileqq"))
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(QQURL + info.actor_qq))) else
                    Toast.makeText(
                        this@ActorIntroduceActivity,
                        context.getString(R.string.install_qq),
                        Toast.LENGTH_LONG
                    ).show()
            }
            text = info.actor_qq.plus(getString(R.string.contact))
        }
        with(actor_wx_tv) {
            if (info.actor_wx.isEmpty()) View.GONE else View.VISIBLE
            text = info.actor_wx.plus(getString(R.string.copy))
            setOnClickListener {
                BaseTools.copyTextContent(this@ActorIntroduceActivity, info.actor_wx)
                Toast.makeText(
                    this@ActorIntroduceActivity,
                    context.getString(R.string.copy),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        with(actor_phone_tv) {
            if (info.actor_phone.isEmpty()) View.GONE else View.VISIBLE

            setOnClickListener {
                BaseTools.callPhone(info.actor_phone, this@ActorIntroduceActivity)
            }
            text = info.actor_phone.plus(getString(R.string.contact))
        }
        with(actor_work_address_tv) {
            if (info.actor_workaddress.isEmpty()) setVisible(true) else setVisible(false)
            text = info.actor_workaddress
        }
        actor_introduction_tv.text = "老师介绍:\n${info.actor_introduce}"
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.mComments.observe(this@ActorIntroduceActivity, Observer {
            click_see_all.text = "点击查看更多"//String.format("点击查看更多(%s)", it.size)// "点击查看更多(${it.size})"
            o_comment_content_tv.text = it[0].from_name.plus(":${it[1].content}")
            w_comment_content_tv.text = it[1].from_name.plus(":${it[1].content}")
        })
    }

    override fun initData() {

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
