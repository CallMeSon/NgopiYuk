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

// Fragment untuk menampilkan daftar kedai kopi yang di-bookmark (favorit)
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
        // Refresh list ketika fragment kembali aktif
        view?.let { loadFavorites(it) }
    }

    // Mengambil data favorit dan mengatur RecyclerView
    private fun loadFavorites(view: View) {
        val rvFavorites: RecyclerView = view.findViewById(R.id.rvFavorites)
        val emptyState: LinearLayout = view.findViewById(R.id.emptyStateLayout)

        // Mengambil data favorit dari SharedPreferences & mencocokkan dengan JSON
        val favoriteIds = FavoritesManager.getFavoriteIds(requireContext())
        val allShops = JsonHelper.getCoffeeShops(requireContext())
        val favoriteList = allShops.filter { favoriteIds.contains(it.id.toString()) }

        if (favoriteList.isEmpty()) {
            // Tampilkan empty state jika tidak ada favorit
            rvFavorites.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            rvFavorites.visibility = View.VISIBLE
            emptyState.visibility = View.GONE

            rvFavorites.layoutManager = LinearLayoutManager(requireContext())
            rvFavorites.adapter = CoffeeShopAdapter(
                shopList = favoriteList,
                onItemClick = { shop -> navigateToDetail(shop) }, // Klik -> masuk detail
                onItemLongClick = { shop -> showBottomSheet(shop) }, // Long click -> bottom sheet
                onBookmarkChanged = { _, isBookmarked ->
                    // Segarkan list jika di-unbookmark
                    if (!isBookmarked) {
                        loadFavorites(view)
                    }
                }
            )
        }
    }

    // Navigasi ke halaman detail kedai kopi
    private fun navigateToDetail(shop: CoffeeShop) {
        val action = FavoritesFragmentDirections.actionFavoritesToCoffeeDetail(shop)
        findNavController().navigate(action)
    }

    // Menampilkan Bottom Sheet menu pilihan
    private fun showBottomSheet(shop: CoffeeShop) {
        val bottomSheet = CoffeeOptionsBottomSheet.newInstance(shop)
        bottomSheet.show(childFragmentManager, CoffeeOptionsBottomSheet.TAG)
    }
}
