package com.android.ngopiyuk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coffee(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val type: String,
    val rating: Double,
    val image: String
) : Parcelable
