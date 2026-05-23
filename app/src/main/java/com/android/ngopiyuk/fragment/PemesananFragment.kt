package com.android.ngopiyuk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.Voucher
import com.android.ngopiyuk.adapter.VoucherAdapter

class PemesananFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pemesanan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyVouchers = listOf(
            Voucher(1, "20%", "Diskon 20% di Roast & Revel", "Berlaku hingga 30 Jun 2026"),
            Voucher(2, "15%", "Diskon 15% untuk Cold Brew di Urban Brew", "Berlaku hingga 15 Jul 2026"),
            Voucher(3, "BUY1", "Buy 1 Get 1 di Bean & Beyond", "Berlaku hingga 20 Jun 2026"),
            Voucher(4, "10%", "Diskon 10% semua menu di Kopi Nusantara", "Berlaku hingga 31 Jul 2026"),
            Voucher(5, "FREE", "Gratis Pastry untuk pembelian kopi di WorkBrew Hub", "Berlaku hingga 25 Jun 2026"),
            Voucher(6, "25%", "Diskon 25% Weekend Special di Rustic Grinder", "Berlaku hingga 30 Jun 2026")
        )

        val rvVouchers: RecyclerView = view.findViewById(R.id.rvVouchers)
        rvVouchers.layoutManager = LinearLayoutManager(requireContext())
        rvVouchers.adapter = VoucherAdapter(dummyVouchers)
    }
}
