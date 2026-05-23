package com.android.ngopiyuk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R

data class Voucher(
    val id: Int,
    val discount: String,
    val title: String,
    val expiry: String
)

class VoucherAdapter(
    private val vouchers: List<Voucher>
) : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {

    class VoucherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDiscount: TextView = view.findViewById(R.id.tvDiscount)
        val tvVoucherTitle: TextView = view.findViewById(R.id.tvVoucherTitle)
        val tvVoucherExpiry: TextView = view.findViewById(R.id.tvVoucherExpiry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_voucher, parent, false)
        return VoucherViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        val voucher = vouchers[position]
        holder.tvDiscount.text = voucher.discount
        holder.tvVoucherTitle.text = voucher.title
        holder.tvVoucherExpiry.text = voucher.expiry
    }

    override fun getItemCount(): Int = vouchers.size
}
