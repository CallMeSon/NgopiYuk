package com.android.ngopiyuk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.model.Coffee

class CoffeeAdapter(
    private val coffeeList: List<Coffee>,
    private val onItemClick: (Coffee) -> Unit,
    private val onItemLongClick: (Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    class CoffeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCoffee: ImageView = view.findViewById(R.id.imgCoffee)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        val context = holder.itemView.context

        holder.tvName.text = coffee.name
        holder.tvType.text = coffee.type
        holder.tvPrice.text = "Rp ${coffee.price}"
        holder.tvRating.text = coffee.rating.toString()

        // Ambil image resource id secara dinamis
        val imageName = if (coffee.image.contains("/")) {
            coffee.image.split("/")[1]
        } else {
            coffee.image
        }

        val imageResId = context.resources.getIdentifier(
            imageName,
            "drawable",
            context.packageName
        ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }

        holder.imgCoffee.setImageResource(imageResId)

        // Click listener → navigate to detail
        holder.itemView.setOnClickListener {
            onItemClick(coffee)
        }

        // Long press → show bottom sheet
        holder.itemView.setOnLongClickListener {
            onItemLongClick(coffee)
            true
        }
    }

    override fun getItemCount(): Int = coffeeList.size
}
