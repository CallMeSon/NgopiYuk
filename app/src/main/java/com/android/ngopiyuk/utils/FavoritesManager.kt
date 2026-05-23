package com.android.ngopiyuk.utils

import android.content.Context
import android.content.SharedPreferences

object FavoritesManager {

    private const val PREF_NAME = "ngopiyuk_favorites"
    private const val KEY_FAVORITE_IDS = "favorite_ids"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getFavoriteIds(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_FAVORITE_IDS, emptySet()) ?: emptySet()
    }

    fun isFavorite(context: Context, coffeeId: Int): Boolean {
        return getFavoriteIds(context).contains(coffeeId.toString())
    }

    fun addFavorite(context: Context, coffeeId: Int) {
        val current = getFavoriteIds(context).toMutableSet()
        current.add(coffeeId.toString())
        getPrefs(context).edit().putStringSet(KEY_FAVORITE_IDS, current).apply()
    }

    fun removeFavorite(context: Context, coffeeId: Int) {
        val current = getFavoriteIds(context).toMutableSet()
        current.remove(coffeeId.toString())
        getPrefs(context).edit().putStringSet(KEY_FAVORITE_IDS, current).apply()
    }

    fun toggleFavorite(context: Context, coffeeId: Int): Boolean {
        return if (isFavorite(context, coffeeId)) {
            removeFavorite(context, coffeeId)
            false
        } else {
            addFavorite(context, coffeeId)
            true
        }
    }
}
