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
import com.android.ngopiyuk.adapter.CoffeeShopAdapter
import com.android.ngopiyuk.model.CoffeeShop
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
        val allShops = JsonHelper.getCoffeeShops(requireContext())
        val favoriteList = allShops.filter { favoriteIds.contains(it.id.toString()) }

        if (favoriteList.isEmpty()) {
            rvFavorites.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            rvFavorites.visibility = View.VISIBLE
            emptyState.visibility = View.GONE

            rvFavorites.layoutManager = LinearLayoutManager(requireContext())
            rvFavorites.adapter = CoffeeShopAdapter(
                shopList = favoriteList,
                onItemClick = { shop -> navigateToDetail(shop) },
                onItemLongClick = { shop -> showBottomSheet(shop) },
                onBookmarkChanged = { _, isBookmarked ->
                    if (!isBookmarked) {
                        loadFavorites(view)
                    }
                }
            )
        }
    }

    private fun navigateToDetail(shop: CoffeeShop) {
        val action = FavoritesFragmentDirections.actionFavoritesToCoffeeDetail(shop)
        findNavController().navigate(action)
    }

    private fun showBottomSheet(shop: CoffeeShop) {
        val bottomSheet = CoffeeOptionsBottomSheet.newInstance(shop)
        bottomSheet.show(childFragmentManager, CoffeeOptionsBottomSheet.TAG)
    }
}
