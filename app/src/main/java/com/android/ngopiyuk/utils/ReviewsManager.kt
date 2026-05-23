package com.android.ngopiyuk.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.ngopiyuk.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ReviewsManager {

    private const val PREFS_NAME = "ngopiyuk_reviews_pref"
    private const val KEY_REVIEW_PREFIX = "reviews_shop_"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Load reviews (loads from SharedPreferences, fallback to initial list from JSON if empty)
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

    // Save reviews to SharedPreferences
    private fun saveReviews(context: Context, shopId: Int, reviews: List<Review>) {
        val prefs = getPrefs(context)
        val key = KEY_REVIEW_PREFIX + shopId
        val json = Gson().toJson(reviews)
        prefs.edit().putString(key, json).apply()
    }

    // Add a new review
    fun addReview(context: Context, shopId: Int, review: Review) {
        val current = getReviews(context, shopId, emptyList()).toMutableList()
        current.add(0, review) // Add to top
        saveReviews(context, shopId, current)
    }

    // Dynamically calculate rating and review count based on initial data + new user reviews
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

        // Calculate count of user-added reviews
        val addedReviews = allReviews.dropLast(initialMockSize)
        val addedCount = addedReviews.size

        if (addedCount == 0) {
            return Pair(initialRating, initialCount)
        }

        // Sum up added ratings
        val addedRatingSum = addedReviews.sumOf { it.rating.toDouble() }

        // Compute total sum and count
        val totalCount = initialCount + addedCount
        val totalRatingSum = (initialRating * initialCount) + addedRatingSum
        val finalRating = totalRatingSum / totalCount

        // Format to 1 decimal place
        val formattedRating = Math.round(finalRating * 10.0) / 10.0
        return Pair(formattedRating, totalCount)
    }
}
