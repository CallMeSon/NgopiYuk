package com.android.ngopiyuk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.CoffeeAdapter
import com.android.ngopiyuk.model.Coffee
import com.android.ngopiyuk.ui.CoffeeOptionsBottomSheet
import com.android.ngopiyuk.utils.FavoritesManager
import com.android.ngopiyuk.utils.JsonHelper

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFavorites(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadFavorites(it) }
    }

    private fun loadFavorites(view: View) {
        val rvFavorites: RecyclerView = view.findViewById(R.id.rvFavorites)
        val emptyState: LinearLayout = view.findViewById(R.id.emptyStateLayout)

        val favoriteIds = FavoritesManager.getFavoriteIds(requireContext())
        val allCoffee = JsonHelper.getCoffeeCatalog(requireContext())
        val favoriteList = allCoffee.filter { favoriteIds.contains(it.id.toString()) }

        if (favoriteList.isEmpty()) {
            rvFavorites.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            rvFavorites.visibility = View.VISIBLE
            emptyState.visibility = View.GONE

            rvFavorites.layoutManager = LinearLayoutManager(requireContext())
            rvFavorites.adapter = CoffeeAdapter(
                coffeeList = favoriteList,
                onItemClick = { coffee -> navigateToDetail(coffee) },
                onItemLongClick = { coffee -> showBottomSheet(coffee) }
            )
        }
    }

    private fun navigateToDetail(coffee: Coffee) {
        val action = FavoritesFragmentDirections.actionFavoritesToCoffeeDetail(coffee)
        findNavController().navigate(action)
    }

    private fun showBottomSheet(coffee: Coffee) {
        val bottomSheet = CoffeeOptionsBottomSheet.newInstance(coffee)
        bottomSheet.show(childFragmentManager, CoffeeOptionsBottomSheet.TAG)
    }
}
