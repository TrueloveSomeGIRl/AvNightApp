package com.cxw.avnight.mode.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActorInfoResult(
    val total: Int,
    val pageTotal: Int,
    val prePage: Int,
    val nextPage: Int,
    val pageSize: Int,
    val currentPage: Int,
    val data: List<ActorInfo>
) : Parcelable


