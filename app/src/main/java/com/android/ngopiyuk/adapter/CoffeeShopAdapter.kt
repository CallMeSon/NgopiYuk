package com.android.ngopiyuk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.model.CoffeeShop
import com.android.ngopiyuk.utils.FavoritesManager

class CoffeeShopAdapter(
    private var shopList: List<CoffeeShop>,
    private val onItemClick: (CoffeeShop) -> Unit,
    private val onItemLongClick: (CoffeeShop) -> Unit,
    private val onBookmarkChanged: ((CoffeeShop, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<CoffeeShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgShop: ImageView = view.findViewById(R.id.imgShop)
        val tvShopName: TextView = view.findViewById(R.id.tvShopName)
        val btnBookmark: ImageView = view.findViewById(R.id.btnBookmark)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val tvReviewCount: TextView = view.findViewById(R.id.tvReviewCount)
        val tvDistance: TextView = view.findViewById(R.id.tvDistance)
        val tvPriceLevel: TextView = view.findViewById(R.id.tvPriceLevel)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee_shop, parent, false)
        return ShopViewHolder(view)
    }

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]
        val context = holder.itemView.context

        holder.tvShopName.text = shop.name
        holder.tvRating.text = shop.rating.toString()
        holder.tvReviewCount.text = formatReviewCount(shop.reviewCount)
        holder.tvDistance.text = shop.distance
        holder.tvPriceLevel.text = shop.priceLevel
        holder.tvCategory.text = shop.category

        // Load image
        val imageName = if (shop.image.contains("/")) {
            shop.image.split("/")[1]
        } else {
            shop.image
        }
        val imageResId = context.resources.getIdentifier(
            imageName, "drawable", context.packageName
        ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }
        holder.imgShop.setImageResource(imageResId)

        // Bookmark state
        val isFav = FavoritesManager.isFavorite(context, shop.id)
        updateBookmarkIcon(holder.btnBookmark, isFav)

        // Bookmark click
        holder.btnBookmark.setOnClickListener {
            val isCurrentlyFavorite = FavoritesManager.isFavorite(context, shop.id)
            if (isCurrentlyFavorite) {
                com.google.android.material.dialog.MaterialAlertDialogBuilder(context)
                    .setTitle("Hapus dari Favorit?")
                    .setMessage("Apakah Anda yakin ingin menghapus ${shop.name} dari daftar kedai kopi favorit Anda?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        FavoritesManager.toggleFavorite(context, shop.id)
                        updateBookmarkIcon(holder.btnBookmark, false)
                        onBookmarkChanged?.invoke(shop, false)
                    }
                    .show()
            } else {
                FavoritesManager.toggleFavorite(context, shop.id)
                updateBookmarkIcon(holder.btnBookmark, true)
                onBookmarkChanged?.invoke(shop, true)
            }
        }

        // Item click → navigate to detail
        holder.itemView.setOnClickListener {
            onItemClick(shop)
        }

        // Long press → show bottom sheet
        holder.itemView.setOnLongClickListener {
            onItemLongClick(shop)
            true
        }
    }

    override fun getItemCount(): Int = shopList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<CoffeeShop>) {
        shopList = newList
        notifyDataSetChanged()
    }

    private fun updateBookmarkIcon(imageView: ImageView, isBookmarked: Boolean) {
        if (isBookmarked) {
            imageView.setImageResource(android.R.drawable.btn_star_big_on)
            imageView.setColorFilter(
                imageView.context.getColor(R.color.bookmark_active)
            )
        } else {
            imageView.setImageResource(android.R.drawable.btn_star_big_off)
            imageView.setColorFilter(
                imageView.context.getColor(R.color.bookmark_inactive)
            )
        }
    }

    private fun formatReviewCount(count: Int): String {
        return when {
            count >= 1000 -> "(${String.format("%.1f", count / 1000.0)}k)"
            else -> "($count)"
        }
    }
}
