package com.cxw.avnight.adapter

import android.text.Html
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
        if (item.childComments.isNotEmpty()) {   //有评论回复
            helper.setGone(R.id.reply_comment_layout, true)
            if (item.childComments.size >= 2 || item.childComments.size == 1) {
                helper.setGone(R.id.comment_iv, false)
                helper.setGone(R.id.total_reply_comment_tv, true)
                helper.setText(
                    R.id.reply_comment_content_tv,
                    Html.fromHtml("<font color='#536DFE'>${item.childComments[0].from_name}: </font>${item.childComments[0].content}")
                ).setText(
                    R.id.total_reply_comment_tv,
                    Html.fromHtml("<font color='#536DFE'>共${item.childComments.size}条评论回复></font>")

                )

            } else {

                helper.setText(
                    R.id.reply_comment_content_tv,
                    Html.fromHtml("<font color='#536DFE'>${item.childComments[0].from_name}: </font>${item.childComments[0].content}")
                ).setText(
                    R.id.total_reply_comment_tv,
                    Html.fromHtml("<font color='#536DFE'>共${item.childComments.size}条评论回复></font>")

                )
                helper.setGone(R.id.comment_iv, true)
                helper.setGone(R.id.total_reply_comment_tv, true)
            }

        } else {
            helper.setGone(R.id.reply_comment_layout, false)
        }



        helper.setText(R.id.comment_user_name_tv, item.from_name)
            .setText(R.id.comment_content_tv, item.content)
            .setText(R.id.comment_time_tv, item.create_time)
            .addOnClickListener(R.id.comment_iv)
            .addOnClickListener(R.id.total_reply_comment_tv)

    }
}