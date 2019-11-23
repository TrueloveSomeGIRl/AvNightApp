package com.cxw.avnight.mode.bean


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ActorInfo(
    val actorImgs: List<ActorImg>,
    val actor_age: Int,
    val actor_bust: String,
    val actor_city: String,
    val actor_evaluate: String,
    val actor_gender: String,
    val actor_height: Int,
    val actor_introduce: String,
    val actor_isinvalid: Int,
    val actor_isverification: Int,
    val actor_name: String,
    val actor_potato: String,
    val actor_phone: String,
    val actor_qq: String,
    val actor_weight: Int,
    val actor_workaddress: String,
    val actor_wx: String,
    val id: Int
) : Parcelable

