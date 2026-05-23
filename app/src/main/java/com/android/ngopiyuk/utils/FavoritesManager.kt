package com.android.ngopiyuk.utils

import android.content.Context
import android.content.SharedPreferences

object FavoritesManager {

    private const val PREF_NAME = "ngopiyuk_bookmarks"
    private const val KEY_FAVORITE_IDS = "bookmarked_ids"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getFavoriteIds(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_FAVORITE_IDS, emptySet()) ?: emptySet()
    }

    fun isFavorite(context: Context, shopId: Int): Boolean {
        return getFavoriteIds(context).contains(shopId.toString())
    }

    fun addFavorite(context: Context, shopId: Int) {
        val current = getFavoriteIds(context).toMutableSet()
        current.add(shopId.toString())
        getPrefs(context).edit().putStringSet(KEY_FAVORITE_IDS, current).apply()
    }

    fun removeFavorite(context: Context, shopId: Int) {
        val current = getFavoriteIds(context).toMutableSet()
        current.remove(shopId.toString())
        getPrefs(context).edit().putStringSet(KEY_FAVORITE_IDS, current).apply()
    }

    fun toggleFavorite(context: Context, shopId: Int): Boolean {
        return if (isFavorite(context, shopId)) {
            removeFavorite(context, shopId)
            false
        } else {
            addFavorite(context, shopId)
            true
        }
    }

    fun clearAllFavorites(context: Context) {
        getPrefs(context).edit().remove(KEY_FAVORITE_IDS).apply()
    }
}
