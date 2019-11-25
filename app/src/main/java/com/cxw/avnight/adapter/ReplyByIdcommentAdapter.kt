package com.cxw.avnight.adapter

import android.text.Html
import android.util.Log
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.ui.loufeng.ReplyCommentActivity

class ReplyByIdCommentAdapter(layoutResId: Int = R.layout.item_reply_comments_layout) :
    BaseQuickAdapter<ChildComment, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: ChildComment) {

        Glide.with(mContext).load(item.from_avatar)
            .into(helper.getView(R.id.reply_comment_user_head_img_iv))
        helper.setText(R.id.reply_comment_user_name_tv, item.from_name)
            .setText(R.id.comment_time_tv, item.create_time)
            .addOnClickListener(R.id.comment_iv)
        if (item.from_id == item.to_id || item.to_id == ReplyCommentActivity.fromId) {
            helper.setText(R.id.reply_comment_tv, item.content)
        } else {
            helper.setText(
                R.id.reply_comment_tv,
                //"回复".plus()
                Html.fromHtml("回复@<font color='#536DFE'>${item.to_name}: </font>${item.content}")
            )
        }


    }
}