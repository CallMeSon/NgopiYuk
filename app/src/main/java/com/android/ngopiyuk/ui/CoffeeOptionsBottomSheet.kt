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
import com.android.ngopiyuk.fragment.CoffeeListFragment
import com.android.ngopiyuk.model.Coffee
import com.android.ngopiyuk.utils.FavoritesManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController

class CoffeeOptionsBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CoffeeOptionsBottomSheet"
        private const val ARG_COFFEE = "arg_coffee"

        fun newInstance(coffee: Coffee): CoffeeOptionsBottomSheet {
            return CoffeeOptionsBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_COFFEE, coffee)
                }
            }
        }
    }

    private lateinit var coffee: Coffee

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_coffee_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        coffee = arguments?.getParcelable(ARG_COFFEE) ?: return dismiss()

        val isFav = FavoritesManager.isFavorite(requireContext(), coffee.id)
        val tvFavoriteOption: TextView = view.findViewById(R.id.tvFavoriteOption)
        val iconFavorite: ImageView = view.findViewById(R.id.iconFavorite)

        if (isFav) {
            tvFavoriteOption.text = getString(R.string.bottom_sheet_remove_favorite)
            iconFavorite.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            tvFavoriteOption.text = getString(R.string.bottom_sheet_add_favorite)
            iconFavorite.setImageResource(android.R.drawable.btn_star_big_off)
        }

        // Opsi Favorit
        view.findViewById<LinearLayout>(R.id.optionFavorite).setOnClickListener {
            val added = FavoritesManager.toggleFavorite(requireContext(), coffee.id)
            val message = if (added) {
                getString(R.string.snackbar_added_favorite, coffee.name)
            } else {
                getString(R.string.snackbar_removed_favorite, coffee.name)
            }
            parentFragment?.view?.let { parentView ->
                Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()
            }
            dismiss()
        }

        // Opsi Bagikan
        view.findViewById<LinearLayout>(R.id.optionShare).setOnClickListener {
            val shareText = getString(
                R.string.share_text,
                coffee.name,
                coffee.price,
                coffee.rating.toString()
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
            // Navigate to detail - handled by parent fragment
            val parentFrag = parentFragment
            if (parentFrag is CoffeeListFragment) {
                // Let parent handle navigation
            }
            // Navigate via NavController from the activity
            try {
                val navController = requireActivity().findNavController(R.id.nav_host_fragment)
                val action = com.android.ngopiyuk.fragment.DashboardFragmentDirections
                    .actionDashboardToCoffeeDetail(coffee)
                navController.navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
