package com.cxw.avnight.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ChildComment

class ReplyByIdCommentAdapter(layoutResId: Int = R.layout.item_reply_comments_layout) :
    BaseQuickAdapter<ChildComment, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ChildComment) {
        Glide.with(mContext).load(item.from_avatar)
            .into(helper.getView(R.id.reply_comment_user_head_img_iv))
        helper.setText(R.id.reply_comment_user_name_tv, item.from_name)
            .setText(R.id.reply_comment_time_tv, item.create_time)
            .setText(R.id.reply_comment_tv, item.content)

    }
}