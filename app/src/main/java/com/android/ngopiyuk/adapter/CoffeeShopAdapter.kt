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

// Adapter RecyclerView untuk menampilkan daftar kedai kopi
class CoffeeShopAdapter(
    private var shopList: List<CoffeeShop>,
    private val onItemClick: (CoffeeShop) -> Unit,
    private val onItemLongClick: (CoffeeShop) -> Unit,
    private val onBookmarkChanged: ((CoffeeShop, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<CoffeeShopAdapter.ShopViewHolder>() {

    // View Holder untuk menampung referensi view item card
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

    // Mengikat data kedai kopi ke elemen UI item card
    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = shopList[position]
        val context = holder.itemView.context

        val (dynamicRating, dynamicCount) = com.android.ngopiyuk.utils.ReviewsManager.getRatingAndCount(
            context, shop.id, shop.rating, shop.reviewCount, shop.initialReviews.size
        )

        holder.tvShopName.text = shop.name
        holder.tvRating.text = dynamicRating.toString()
        holder.tvReviewCount.text = formatReviewCount(dynamicCount)
        holder.tvDistance.text = shop.distance
        holder.tvPriceLevel.text = shop.priceLevel
        holder.tvCategory.text = shop.category

        // Memuat Gambar dari nama drawable
        val imageName = if (shop.image.contains("/")) {
            shop.image.split("/")[1]
        } else {
            shop.image
        }
        val imageResId = context.resources.getIdentifier(
            imageName, "drawable", context.packageName
        ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }
        holder.imgShop.setImageResource(imageResId)

        // Set status ikon bookmark (aktif/nonaktif)
        val isFav = FavoritesManager.isFavorite(context, shop.id)
        updateBookmarkIcon(holder.btnBookmark, isFav)

        // Klik tombol Bookmark (dengan konfirmasi unbookmark)
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

        // Klik biasa -> Masuk detail kedai kopi
        holder.itemView.setOnClickListener {
            onItemClick(shop)
        }

        // Klik lama -> Buka bottom sheet pilihan
        holder.itemView.setOnLongClickListener {
            onItemLongClick(shop)
            true
        }
    }

    override fun getItemCount(): Int = shopList.size

    // Memperbarui list data kedai kopi secara dinamis
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<CoffeeShop>) {
        shopList = newList
        notifyDataSetChanged()
    }

    // Update visual ikon bookmark
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

    // Format jumlah review (misal: 1500 -> 1.5k)
    private fun formatReviewCount(count: Int): String {
        return when {
            count >= 1000 -> "(${String.format("%.1f", count / 1000.0)}k)"
            else -> "($count)"
        }
    }
}
