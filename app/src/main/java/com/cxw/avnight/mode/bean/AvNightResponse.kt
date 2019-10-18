package com.cxw.avnight.mode.bean

//data class AvNightResponse<out T>(val errCode: Int, val errMsg: String, val data: T)

data class AvNightResponse<out T>(val errCode: String, val errMsg: String, val data: T)