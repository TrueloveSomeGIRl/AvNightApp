package com.cxw.avnight.mode.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActorImg(
    val img_actorid: Int,
    val img_creattime: String,
    val img_h: Double,
    val img_id: Int,
    val img_info: String,
    val img_url: String,
    val img_w: Double,
    val img_weight: Int
) :  Parcelable