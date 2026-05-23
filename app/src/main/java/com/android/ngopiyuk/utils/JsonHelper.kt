package com.android.ngopiyuk.utils

import android.content.Context
import com.android.ngopiyuk.model.CoffeeShop
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonHelper {
    fun getCoffeeShops(context: Context): List<CoffeeShop> {
        val jsonString: String
        try {
            jsonString = context.assets.open("coffee_shops.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        val listType = object : TypeToken<List<CoffeeShop>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}
