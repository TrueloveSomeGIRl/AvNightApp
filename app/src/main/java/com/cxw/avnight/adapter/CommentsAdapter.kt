package com.cxw.avnight.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.Comments

class CommentsAdapter(layoutResId: Int = R.layout.item_comment_actor) :
    BaseQuickAdapter<Comments, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Comments) {
        Glide.with(mContext).load(item.from_avatar)
            .into(helper.getView(R.id.comment_user_head_img_iv))
        if (item.childComments.isNotEmpty()) {
            helper.setGone(R.id.reply_comment_layout, true)
            if (item.childComments.size > 1) {
                helper.setText(R.id.reply_comment_content_tv, item.childComments[0].content)
                    .setText(R.id.reply_tow_comment_content_tv, item.childComments[1].content)
            } else if (item.childComments.size == 1) {
                helper.setGone(R.id.reply_tow_comment_content_tv, false)
                helper.setGone(R.id.total_reply_comment_tv, false)
                helper.setText(R.id.reply_comment_content_tv, item.childComments[0].content)
            }
        } else {
            helper.setGone(R.id.reply_comment_layout, false)
        }



        helper.setText(R.id.comment_user_name_tv, item.from_name)
            .setText(R.id.comment_content_tv, item.content)
           .setText(R.id.comment_time_tv, item.create_time)
            .setText(R.id.comment_iv, item.create_time)

    }
}