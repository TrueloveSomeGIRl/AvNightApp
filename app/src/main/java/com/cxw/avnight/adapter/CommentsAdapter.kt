package com.cxw.avnight.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.Comments

class CommentsAdapter(layoutResId: Int = R.layout.item_comment_actor) :
        BaseQuickAdapter<Comments, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Comments) {
        Glide.with(mContext).load(item.from_avatar).into(helper.getView(R.id.comment_user_head_img_iv))
        if (item.childComments.isNotEmpty())
            helper.setGone(R.id.reply_comment_layout, true)
        else helper.setGone(R.id.reply_comment_layout, false)

        helper.setText(R.id.comment_user_name_tv, item.from_name)
                .setText(R.id.comment_content_tv, item.content)
                .setText(R.id.reply_comment_content_tv, item.childComments[0].from_name.plus(":item.childComments[0].content"))
                .setText(R.id.comment_time_tv, item.create_time)
                .setText(R.id.time, item.create_time)
    }
}