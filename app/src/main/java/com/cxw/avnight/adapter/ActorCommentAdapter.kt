package com.cxw.avnight.adapter


import android.util.Log
import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cxw.avnight.App
import com.cxw.avnight.R
import com.cxw.avnight.mode.bean.ActorCommentEntity
import com.cxw.avnight.mode.bean.Comments

import com.drakeet.multitype.ItemViewBinder


class ActorCommentAdapter : ItemViewBinder<ActorCommentEntity, ActorCommentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, item: ActorCommentEntity) {

        holder.commentTv.text = item.comments.content
        holder.commentUserNameTv.text =item.comments.from_name
        Glide.with(App.CONTEXT).load(item.comments.from_avatar)
            .into(holder.commentUserIv)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_comment_actor, parent, false))
    }


    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentUserNameTv: TextView = itemView.findViewById(R.id.comment_user_name_tv)
        val commentTv: TextView = itemView.findViewById(R.id.comment_content_tv)
        val commentUserIv: ImageView = itemView.findViewById(R.id.comment_user_head_img_iv)
    }

}