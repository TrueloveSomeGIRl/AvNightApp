package com.cxw.avnight.mode.bean

data class RegisteredEntity(
    val Id: Int,
    val code: String,
    val email: String,
    val head_portrait: String,
    val idd: Int,
    val name: String,
    val password: String,
    val sex: String
)