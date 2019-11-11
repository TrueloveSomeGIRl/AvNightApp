package com.cxw.avnight.mode.bean

data class LoginEntity(
    val token: String,
    val userEmail: String,
    val userHeadImg: String,
    val userId: Int,
    val userName: String,
    val userSex: String
)