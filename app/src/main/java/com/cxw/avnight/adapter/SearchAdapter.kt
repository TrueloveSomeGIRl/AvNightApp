package com.cxw.avnight.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ActorInfo
import com.cxw.avnight.mode.bean.Comments

class SearchAdapter(layoutResId: Int = R.layout.item_search_actor_layout) :
    BaseQuickAdapter<ActorInfo, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: ActorInfo?) {

    }

}