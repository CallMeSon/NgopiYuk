package com.android.ngopiyuk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String,
    val name: String,
    val rating: Float,
    val comment: String,
    val timeAgo: String,
    val avatarColor: String
) : Parcelable
