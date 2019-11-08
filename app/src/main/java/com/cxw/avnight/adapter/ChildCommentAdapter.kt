
package com.cxw.avnight.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cxw.avnight.App
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ChildComment
import com.cxw.avnight.mode.bean.ChildCommentEntity
import com.cxw.avnight.mode.bean.Comments
import com.drakeet.multitype.ItemViewBinder


class ChildCommentAdapter : ItemViewBinder<ChildCommentEntity, ChildCommentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, item: ChildCommentEntity) {
        holder.replyCommentUserNameTv.text =item.childComment.from_name
        holder.replyCommentTv.text = item.childComment.content
        Glide.with(App.CONTEXT).load(item.childComment.from_avatar)
            .into(holder.replyCommentUserIv)
    }



    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_reple_comment,parent,false))

    }


    open class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val replyCommentUserNameTv: TextView = itemView.findViewById(R.id.reply_comment_user_name_iv)
        val replyCommentTv: TextView = itemView.findViewById(R.id.reply_comment)
        val replyCommentUserIv: ImageView = itemView.findViewById(R.id.reply_comment_user_head_img_iv)

    }



}