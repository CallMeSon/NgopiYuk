package com.android.ngopiyuk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeShop(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val reviewCount: Int,
    val distance: String,
    val priceLevel: String,
    val category: String,
    val tags: List<String>,
    val image: String,
    val address: String,
    val openHours: String,
    val facilities: List<String>,
    val menuItems: List<MenuItem>,
    val initialReviews: List<Review>
) : Parcelable
