package com.cxw.avnight.mode.bean

data class Comments(
    val childComments: List<ChildComment>,
    val content: String,
    val create_time: String,
    val from_avatar: String,
    val from_id: Int,
    val from_name: String,
    val id: Int,
    val like_num: Int,
    val owner_id: Int,
    val type: Int
)