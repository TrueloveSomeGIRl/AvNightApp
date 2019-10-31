package com.cxw.avnight.adapter


import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ActorInfo

class LouFengAdapter(layoutResId: Int = R.layout.loufeng_item_actor_layout) :
    BaseQuickAdapter<ActorInfo, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ActorInfo) {
        Glide.with(mContext).load(item.actorImgs[0].img_url).into(helper.getView(R.id.actor_iv))
        helper.setText(R.id.actor_tv,item.actor_name)
    }
}