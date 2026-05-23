package com.android.ngopiyuk.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.ngopiyuk.R
import com.android.ngopiyuk.model.CoffeeShop
import com.android.ngopiyuk.utils.FavoritesManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import com.android.ngopiyuk.fragment.FavoritesFragment
import com.android.ngopiyuk.fragment.DashboardFragment

class CoffeeOptionsBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CoffeeOptionsBottomSheet"
        private const val ARG_SHOP = "arg_shop"

        fun newInstance(shop: CoffeeShop): CoffeeOptionsBottomSheet {
            return CoffeeOptionsBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHOP, shop)
                }
            }
        }
    }

    private lateinit var shop: CoffeeShop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_coffee_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        shop = arguments?.getParcelable(ARG_SHOP) ?: return dismiss()

        val isFav = FavoritesManager.isFavorite(requireContext(), shop.id)
        val tvFavoriteOption: TextView = view.findViewById(R.id.tvFavoriteOption)
        val iconFavorite: ImageView = view.findViewById(R.id.iconFavorite)

        if (isFav) {
            tvFavoriteOption.text = getString(R.string.bottom_sheet_remove_favorite)
            iconFavorite.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            tvFavoriteOption.text = getString(R.string.bottom_sheet_add_favorite)
            iconFavorite.setImageResource(android.R.drawable.btn_star_big_off)
        }

        // Opsi Bookmark
        view.findViewById<LinearLayout>(R.id.optionFavorite).setOnClickListener {
            val isCurrentlyFavorite = FavoritesManager.isFavorite(requireContext(), shop.id)
            if (isCurrentlyFavorite) {
                com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Hapus dari Favorit?")
                    .setMessage("Apakah Anda yakin ingin menghapus ${shop.name} dari daftar kedai kopi favorit Anda?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        FavoritesManager.toggleFavorite(requireContext(), shop.id)
                        parentFragment?.view?.let { parentView ->
                            Snackbar.make(parentView, getString(R.string.snackbar_unbookmarked, shop.name), Snackbar.LENGTH_SHORT).show()
                        }
                        val parent = parentFragment
                        if (parent is FavoritesFragment) {
                            parent.onResume()
                        } else if (parent is DashboardFragment) {
                            parent.onResume()
                        }
                        dismiss()
                    }
                    .show()
            } else {
                FavoritesManager.toggleFavorite(requireContext(), shop.id)
                parentFragment?.view?.let { parentView ->
                    Snackbar.make(parentView, getString(R.string.snackbar_bookmarked, shop.name), Snackbar.LENGTH_SHORT).show()
                }
                val parent = parentFragment
                if (parent is FavoritesFragment) {
                    parent.onResume()
                } else if (parent is DashboardFragment) {
                    parent.onResume()
                }
                dismiss()
            }
        }

        // Opsi Bagikan
        view.findViewById<LinearLayout>(R.id.optionShare).setOnClickListener {
            val shareText = getString(
                R.string.share_text,
                shop.name,
                shop.rating.toString()
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
            dismiss()
        }

        // Opsi Lihat Detail
        view.findViewById<LinearLayout>(R.id.optionDetail).setOnClickListener {
            dismiss()
            try {
                val navController = requireActivity().findNavController(R.id.nav_host_fragment)
                val action = com.android.ngopiyuk.fragment.DashboardFragmentDirections
                    .actionDashboardToCoffeeDetail(shop)
                navController.navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
