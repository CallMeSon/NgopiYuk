package com.android.ngopiyuk.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.ngopiyuk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class CoffeeDetailFragment : Fragment() {

    private val args: CoffeeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_detail, container, false)
    }

    @SuppressLint("DefaultLocale", "DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffee = args.coffee

        // Bind data
        val imgDetail: ImageView = view.findViewById(R.id.imgDetailCoffee)
        val tvName: TextView = view.findViewById(R.id.tvDetailName)
        val tvRating: TextView = view.findViewById(R.id.tvDetailRating)
        val chipType: Chip = view.findViewById(R.id.chipDetailType)
        val tvPrice: TextView = view.findViewById(R.id.tvDetailPrice)
        val tvDescription: TextView = view.findViewById(R.id.tvDetailDescription)
        val btnAddOrder: MaterialButton = view.findViewById(R.id.btnAddOrder)
        val btnDelete: MaterialButton = view.findViewById(R.id.btnDelete)

        tvName.text = coffee.name
        tvRating.text = coffee.rating.toString()
        chipType.text = coffee.type
        tvPrice.text = String.format("Rp %,d", coffee.price)
        tvDescription.text = coffee.description

        // Load image
        val imageName = if (coffee.image.contains("/")) {
            coffee.image.split("/")[1]
        } else {
            coffee.image
        }
        val imageResId = requireContext().resources.getIdentifier(
            imageName, "drawable", requireContext().packageName
        ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }
        imgDetail.setImageResource(imageResId)

        // Tombol "Tambah ke Pesanan" → Snackbar
        btnAddOrder.setOnClickListener {
            Snackbar.make(
                view,
                getString(R.string.snackbar_added_order, coffee.name),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.snackbar_undo)) {
                // Undo action (placeholder)
            }.setAnchorView(btnAddOrder)
                .show()
        }

        // Tombol "Hapus" → MaterialAlertDialog
        btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.dialog_delete_title))
                .setMessage(getString(R.string.dialog_delete_message, coffee.name))
                .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(getString(R.string.dialog_delete_confirm)) { _, _ ->
                    Snackbar.make(
                        view,
                        getString(R.string.dialog_deleted_success, coffee.name),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    // Navigate back
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                .show()
        }
    }
}
