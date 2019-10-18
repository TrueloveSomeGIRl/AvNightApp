package com.cxw.avnight.mode.bean

data class ChildComment(
    val comment_id: Int,
    val content: String,
    val create_time: String,
    val from_avatar: String,
    val from_id: Int,
    val from_name: String,
    val id: Int,
    val to_avatar: String,
    val to_id: Int,
    val to_name: String,
    val update_time: String
)