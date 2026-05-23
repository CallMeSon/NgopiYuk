package com.android.ngopiyuk.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.CoffeeShopAdapter
import com.android.ngopiyuk.model.CoffeeShop
import com.android.ngopiyuk.ui.CoffeeOptionsBottomSheet
import com.android.ngopiyuk.utils.JsonHelper
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DashboardFragment : Fragment() {

    private lateinit var allShops: List<CoffeeShop>
    private lateinit var adapter: CoffeeShopAdapter
    private var currentSearchQuery: String = ""
    private var currentFilterTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load data
        allShops = JsonHelper.getCoffeeShops(requireContext())

        // Setup RecyclerView
        val rvCoffeeShops: RecyclerView = view.findViewById(R.id.rvCoffeeShops)
        rvCoffeeShops.layoutManager = LinearLayoutManager(requireContext())
        adapter = CoffeeShopAdapter(
            shopList = allShops,
            onItemClick = { shop -> navigateToDetail(shop) },
            onItemLongClick = { shop -> showBottomSheet(shop) }
        )
        rvCoffeeShops.adapter = adapter

        // Setup Search
        val etSearch: EditText = view.findViewById(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentSearchQuery = s.toString().trim()
                applyFilters()
            }
        })

        // Setup Chip Filters
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            currentFilterTag = if (checkedIds.isEmpty()) {
                null
            } else {
                val chipId = checkedIds.first()
                when (chipId) {
                    R.id.chipModern -> "Modern"
                    R.id.chipTraditional -> "Traditional"
                    R.id.chipWorkFriendly -> "Work-friendly"
                    else -> null
                }
            }
            applyFilters()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh bookmark states
        adapter.notifyDataSetChanged()
    }

    private fun applyFilters() {
        var filtered = allShops

        // Filter by search query
        if (currentSearchQuery.isNotEmpty()) {
            filtered = filtered.filter {
                it.name.contains(currentSearchQuery, ignoreCase = true)
            }
        }

        // Filter by tag
        if (currentFilterTag != null) {
            filtered = filtered.filter {
                it.tags.contains(currentFilterTag)
            }
        }

        adapter.updateList(filtered)
    }

    private fun navigateToDetail(shop: CoffeeShop) {
        val action = DashboardFragmentDirections.actionDashboardToCoffeeDetail(shop)
        findNavController().navigate(action)
    }

    private fun showBottomSheet(shop: CoffeeShop) {
        val bottomSheet = CoffeeOptionsBottomSheet.newInstance(shop)
        bottomSheet.show(childFragmentManager, CoffeeOptionsBottomSheet.TAG)
    }
}
