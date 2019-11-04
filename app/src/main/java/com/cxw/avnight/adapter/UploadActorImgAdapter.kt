package com.cxw.avnight.adapter

import android.net.Uri
import android.util.Log

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R

class UploadActorImgAdapter(layoutResId: Int = R.layout.upload_actor_img_item_layout) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: String) {
        Glide.with(mContext).load(item).into(helper.getView(R.id.iv))
        helper.addOnClickListener(R.id.delete)
    }

}