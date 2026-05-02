package com.android.ngopiyuk.utils

import android.content.Context
import com.android.ngopiyuk.model.Coffee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonHelper {
    fun getCoffeeCatalog(context: Context): List<Coffee> {
        val jsonString: String
        try {
            jsonString = context.assets.open("coffee_catalog.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        val listType = object : TypeToken<List<Coffee>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}
