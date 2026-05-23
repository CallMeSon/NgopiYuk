package com.android.ngopiyuk.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.ngopiyuk.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Pengelola data review kedai kopi (Singleton) menggunakan SharedPreferences
object ReviewsManager {

    private const val PREFS_NAME = "ngopiyuk_reviews_pref"
    private const val KEY_REVIEW_PREFIX = "reviews_shop_"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Ambil ulasan dari SharedPreferences (fallback ke initialReviews bawaan)
    fun getReviews(context: Context, shopId: Int, initialReviews: List<Review>): List<Review> {
        val prefs = getPrefs(context)
        val key = KEY_REVIEW_PREFIX + shopId
        val json = prefs.getString(key, null)

        return if (json != null) {
            val type = object : TypeToken<List<Review>>() {}.type
            Gson().fromJson(json, type)
        } else {
            saveReviews(context, shopId, initialReviews)
            initialReviews
        }
    }

    // Simpan daftar ulasan ke SharedPreferences
    private fun saveReviews(context: Context, shopId: Int, reviews: List<Review>) {
        val prefs = getPrefs(context)
        val key = KEY_REVIEW_PREFIX + shopId
        val json = Gson().toJson(reviews)
        prefs.edit().putString(key, json).apply()
    }

    // Tambah ulasan baru ke baris teratas list ulasan
    fun addReview(context: Context, shopId: Int, review: Review) {
        val initialReviews = JsonHelper.getCoffeeShops(context).find { it.id == shopId }?.initialReviews ?: emptyList()
        val current = getReviews(context, shopId, initialReviews).toMutableList()
        current.add(0, review)
        saveReviews(context, shopId, current)
    }

    // Hitung rating rata-rata dan total ulasan secara dinamis (menggabungkan review bawaan + review user)
    fun getRatingAndCount(
        context: Context,
        shopId: Int,
        initialRating: Double,
        initialCount: Int,
        initialMockSize: Int
    ): Pair<Double, Int> {
        val prefs = getPrefs(context)
        val key = KEY_REVIEW_PREFIX + shopId
        val json = prefs.getString(key, null)
        
        val allReviews = if (json != null) {
            val type = object : TypeToken<List<Review>>() {}.type
            Gson().fromJson<List<Review>>(json, type)
        } else {
            emptyList()
        }

        // Dapatkan ulasan baru buatan user
        val addedReviews = allReviews.dropLast(initialMockSize)
        val addedCount = addedReviews.size

        if (addedCount == 0) {
            return Pair(initialRating, initialCount)
        }

        val addedRatingSum = addedReviews.sumOf { it.rating }

        // Hitung total rating rata-rata baru
        val totalCount = initialCount + addedCount
        val totalRatingSum = (initialRating * initialCount) + addedRatingSum
        val finalRating = totalRatingSum / totalCount

        // Bulatkan 1 tempat desimal
        val formattedRating = Math.round(finalRating * 10.0) / 10.0
        return Pair(formattedRating, totalCount)
    }
}
