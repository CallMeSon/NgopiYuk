package com.android.ngopiyuk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.CoffeeAdapter
import com.android.ngopiyuk.model.Coffee
import com.android.ngopiyuk.ui.CoffeeOptionsBottomSheet
import com.android.ngopiyuk.utils.JsonHelper

class CoffeeListFragment : Fragment() {

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String): CoffeeListFragment {
            return CoffeeListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString(ARG_CATEGORY) ?: "Semua"

        // Ambil dan filter data
        val allCoffee = JsonHelper.getCoffeeCatalog(requireContext())
        val filteredList = if (category == "Semua") {
            allCoffee
        } else {
            allCoffee.filter { it.type == category }
        }

        val rvCoffee: RecyclerView = view.findViewById(R.id.rvCoffeeList)
        rvCoffee.layoutManager = LinearLayoutManager(requireContext())
        rvCoffee.adapter = CoffeeAdapter(
            coffeeList = filteredList,
            onItemClick = { coffee -> navigateToDetail(coffee) },
            onItemLongClick = { coffee -> showBottomSheet(coffee) }
        )
    }

    private fun navigateToDetail(coffee: Coffee) {
        // Gunakan parent fragment (DashboardFragment) NavController
        val parentNavController = requireParentFragment().requireParentFragment().findNavController()
        val action = DashboardFragmentDirections.actionDashboardToCoffeeDetail(coffee)
        parentNavController.navigate(action)
    }

    private fun showBottomSheet(coffee: Coffee) {
        val bottomSheet = CoffeeOptionsBottomSheet.newInstance(coffee)
        bottomSheet.show(childFragmentManager, CoffeeOptionsBottomSheet.TAG)
    }
}
