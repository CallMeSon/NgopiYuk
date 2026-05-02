package com.android.ngopiyuk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.CoffeeAdapter
import com.android.ngopiyuk.utils.JsonHelper

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Membaca data dari JSON
        val coffeeList = JsonHelper.getCoffeeCatalog(requireContext())

        // Inisialisasi RecyclerView
        val rvCoffee: RecyclerView = view.findViewById(R.id.rvCoffee)
        rvCoffee.layoutManager = LinearLayoutManager(requireContext())
        rvCoffee.adapter = CoffeeAdapter(coffeeList)
    }
}
