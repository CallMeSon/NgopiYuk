package com.android.ngopiyuk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val name: String,
    val description: String,
    val price: String,
    val category: String, // "Signature Brews" or "Pastries"
    val imageName: String
) : Parcelable
