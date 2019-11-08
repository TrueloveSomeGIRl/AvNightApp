package com.cxw.avnight.mode.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Result<T>(
    val total: Int,
    val pageTotal: Int,
    val prePage: Int,
    val nextPage: Int,
    val pageSize: Int,
    val currentPage: Int,
    val data: List<T>
)


