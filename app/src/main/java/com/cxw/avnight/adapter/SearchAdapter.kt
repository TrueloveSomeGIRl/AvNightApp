package com.cxw.avnight.adapter


import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ActorInfo


class SearchAdapter(layoutResId: Int = R.layout.item_search_actor_layout) :
    BaseQuickAdapter<ActorInfo, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ActorInfo) {
        Glide.with(mContext).load(item.actorImgs[0].img_url).into(helper.getView(R.id.actor_iv))
        helper.setText(R.id.actor_name_tv, mContext.getString(R.string.actor_age).plus(": ${item.actor_name}"))
            .setText(R.id.actor_weight_tv, mContext.getString(R.string.actor_bust).plus(": ${item.actor_weight}"))
            .setText(R.id.actor_age_tv, mContext.getString(R.string.actor_age).plus(": ${item.actor_age}"))
            .setText(R.id.actor_bust_tv, mContext.getString(R.string.actor_bust).plus(": ${item.actor_bust}"))
            .setText(R.id.actor_height_tv,mContext.getString(R.string.actor_height).plus(": ${item.actor_height}"))

    }

}