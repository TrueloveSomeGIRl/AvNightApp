package com.cxw.avnight.mode.bean

data class UpdateApp (
    val newVersion: Int,
    val apkUrl: String,
    val md5: String,
    val isUpdate: Int,
    val updateDescription: String,
    val forceUpdate: Int,
    val minVersion: Int
)
