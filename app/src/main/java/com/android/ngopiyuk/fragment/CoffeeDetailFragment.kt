package com.android.ngopiyuk.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.ngopiyuk.R
import com.android.ngopiyuk.model.CoffeeShop
import com.android.ngopiyuk.utils.FavoritesManager
import com.android.ngopiyuk.utils.ReviewsManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.UUID

class CoffeeDetailFragment : Fragment() {

    private val args: CoffeeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coffee_detail, container, false)
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shop = args.coffee

        // Bind data
        val imgDetail: ImageView = view.findViewById(R.id.imgDetailCoffee)
        val tvName: TextView = view.findViewById(R.id.tvDetailName)
        val tvRating: TextView = view.findViewById(R.id.tvDetailRating)
        val tvReviewCount: TextView = view.findViewById(R.id.tvDetailReviewCount)
        val tvAddress: TextView = view.findViewById(R.id.tvDetailAddress)
        val tvOpenHours: TextView = view.findViewById(R.id.tvDetailOpenHours)
        val tvDescription: TextView = view.findViewById(R.id.tvDetailDescription)
        val chipGroupFacilities: ChipGroup = view.findViewById(R.id.chipGroupFacilities)
        val btnBookmark: MaterialButton = view.findViewById(R.id.btnBookmark)
        val btnShare: MaterialButton = view.findViewById(R.id.btnShare)

        tvName.text = shop.name
        
        // Initial binding for rating and reviews (will be dynamically overwritten by populateReviews)
        tvRating.text = shop.rating.toString()
        val reviewText = when {
            shop.reviewCount >= 1000 -> "(${String.format("%.1f", shop.reviewCount / 1000.0)}k)"
            else -> "(${shop.reviewCount})"
        }
        tvReviewCount.text = reviewText
        
        tvAddress.text = shop.address
        tvOpenHours.text = shop.openHours
        tvDescription.text = shop.description

        // Load image
        val imageName = if (shop.image.contains("/")) {
            shop.image.split("/")[1]
        } else {
            shop.image
        }
        val imageResId = requireContext().resources.getIdentifier(
            imageName, "drawable", requireContext().packageName
        ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }
        imgDetail.setImageResource(imageResId)

        // Add facility chips
        shop.facilities.forEach { facility ->
            val chip = Chip(requireContext()).apply {
                text = facility
                isClickable = false
                isCheckable = false
                setChipBackgroundColorResource(R.color.coffee_cream)
            }
            chipGroupFacilities.addView(chip)
        }

        // Update bookmark button state
        updateBookmarkButton(btnBookmark, FavoritesManager.isFavorite(requireContext(), shop.id))

        // Bookmark button
        btnBookmark.setOnClickListener {
            val isCurrentlyFavorite = FavoritesManager.isFavorite(requireContext(), shop.id)
            if (isCurrentlyFavorite) {
                // Show MaterialAlertDialog to confirm unbookmark (Opsi B)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Hapus dari Favorit?")
                    .setMessage("Apakah Anda yakin ingin menghapus ${shop.name} dari daftar kedai kopi favorit Anda?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        FavoritesManager.toggleFavorite(requireContext(), shop.id)
                        updateBookmarkButton(btnBookmark, false)
                        Snackbar.make(view, getString(R.string.snackbar_unbookmarked, shop.name), Snackbar.LENGTH_SHORT).show()
                    }
                    .show()
            } else {
                // Add immediately
                FavoritesManager.toggleFavorite(requireContext(), shop.id)
                updateBookmarkButton(btnBookmark, true)
                Snackbar.make(view, getString(R.string.snackbar_bookmarked, shop.name), Snackbar.LENGTH_SHORT).show()
            }
        }

        // Share button
        btnShare.setOnClickListener {
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
        }

        // Populate menus and reviews
        populateSignatureMenus(view, shop.menuItems)
        populateReviews(view, shop)

        // Setup write review button
        val btnWriteReview: MaterialButton = view.findViewById(R.id.btnWriteReview)
        btnWriteReview.setOnClickListener {
            showAddReviewDialog(view, shop)
        }
    }

    private fun updateBookmarkButton(button: MaterialButton, isBookmarked: Boolean) {
        if (isBookmarked) {
            button.text = "Unbookmark"
            button.setIconResource(android.R.drawable.btn_star_big_on)
        } else {
            button.text = getString(R.string.detail_bookmark)
            button.setIconResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun populateSignatureMenus(view: View, menuItems: List<com.android.ngopiyuk.model.MenuItem>) {
        val menuContainerSignatureBrews: LinearLayout = view.findViewById(R.id.menuContainerSignatureBrews)
        val menuContainerPastries: LinearLayout = view.findViewById(R.id.menuContainerPastries)

        menuContainerSignatureBrews.removeAllViews()
        menuContainerPastries.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        menuItems.forEach { menuItem ->
            val menuView = inflater.inflate(R.layout.item_menu_detail, null)
            val imgMenu: ImageView = menuView.findViewById(R.id.imgMenu)
            val tvMenuName: TextView = menuView.findViewById(R.id.tvMenuName)
            val tvMenuDescription: TextView = menuView.findViewById(R.id.tvMenuDescription)
            val tvMenuPrice: TextView = menuView.findViewById(R.id.tvMenuPrice)

            tvMenuName.text = menuItem.name
            tvMenuDescription.text = menuItem.description
            tvMenuPrice.text = menuItem.price

            // Load menu image
            val imageResId = requireContext().resources.getIdentifier(
                menuItem.imageName, "drawable", requireContext().packageName
            ).let { if (it == 0) android.R.drawable.ic_menu_gallery else it }
            imgMenu.setImageResource(imageResId)

            if (menuItem.category == "Signature Brews") {
                menuContainerSignatureBrews.addView(menuView)
            } else {
                menuContainerPastries.addView(menuView)
            }
        }
    }

    private fun populateReviews(view: View, shop: CoffeeShop) {
        val reviewsContainer: LinearLayout = view.findViewById(R.id.reviewsContainer)
        reviewsContainer.removeAllViews()

        val reviews = ReviewsManager.getReviews(requireContext(), shop.id, shop.initialReviews)
        val inflater = LayoutInflater.from(requireContext())

        // Calculate and update dynamic average rating and count in the header views
        val (updatedRating, updatedCount) = ReviewsManager.getRatingAndCount(
            requireContext(), shop.id, shop.rating, shop.reviewCount, shop.initialReviews.size
        )

        val tvRating: TextView = view.findViewById(R.id.tvDetailRating)
        val tvReviewCount: TextView = view.findViewById(R.id.tvDetailReviewCount)

        tvRating.text = updatedRating.toString()
        val reviewText = when {
            updatedCount >= 1000 -> "(${String.format("%.1f", updatedCount / 1000.0)}k)"
            else -> "(${updatedCount})"
        }
        tvReviewCount.text = reviewText

        // Populate list
        reviews.forEach { review ->
            val reviewView = inflater.inflate(R.layout.item_review_detail, null)
            val tvAvatarInitials: TextView = reviewView.findViewById(R.id.tvAvatarInitials)
            val cardAvatar: androidx.cardview.widget.CardView = reviewView.findViewById(R.id.cardAvatar)
            val tvReviewName: TextView = reviewView.findViewById(R.id.tvReviewName)
            val tvReviewTime: TextView = reviewView.findViewById(R.id.tvReviewTime)
            val tvReviewComment: TextView = reviewView.findViewById(R.id.tvReviewComment)

            // Avatar Initials
            val initials = review.name.split(" ")
                .mapNotNull { it.firstOrNull() }
                .joinToString("")
                .uppercase()
            tvAvatarInitials.text = if (initials.length > 2) initials.substring(0, 2) else initials

            // Avatar Background Color
            try {
                val colorInt = android.graphics.Color.parseColor(review.avatarColor)
                cardAvatar.setCardBackgroundColor(colorInt)
            } catch (e: Exception) {
                cardAvatar.setCardBackgroundColor(requireContext().getColor(R.color.coffee_primary))
            }

            tvReviewName.text = review.name
            tvReviewTime.text = review.timeAgo
            tvReviewComment.text = review.comment

            // Star icons binding
            val stars = listOf<ImageView>(
                reviewView.findViewById(R.id.imgStar1),
                reviewView.findViewById(R.id.imgStar2),
                reviewView.findViewById(R.id.imgStar3),
                reviewView.findViewById(R.id.imgStar4),
                reviewView.findViewById(R.id.imgStar5)
            )

            for (i in 0 until 5) {
                if (i < review.rating.toInt()) {
                    stars[i].setImageResource(android.R.drawable.btn_star_big_on)
                    stars[i].setColorFilter(android.graphics.Color.parseColor("#FFB300"))
                } else {
                    stars[i].setImageResource(android.R.drawable.btn_star_big_off)
                    stars[i].setColorFilter(android.graphics.Color.parseColor("#D4C3BE"))
                }
            }

            reviewsContainer.addView(reviewView)
        }
    }

    private fun showAddReviewDialog(view: View, shop: CoffeeShop) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_review, null)
        bottomSheetDialog.setContentView(dialogView)

        val etReviewerName: android.widget.EditText = dialogView.findViewById(R.id.etReviewerName)
        val ratingBarReview: android.widget.RatingBar = dialogView.findViewById(R.id.ratingBarReview)
        val etReviewComment: android.widget.EditText = dialogView.findViewById(R.id.etReviewComment)
        val btnCancelReview: View = dialogView.findViewById(R.id.btnCancelReview)
        val btnSubmitReview: View = dialogView.findViewById(R.id.btnSubmitReview)

        btnCancelReview.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        btnSubmitReview.setOnClickListener {
            val name = etReviewerName.text.toString().trim()
            val comment = etReviewComment.text.toString().trim()
            val rating = ratingBarReview.rating

            if (name.isEmpty() || comment.isEmpty()) {
                Snackbar.make(dialogView, getString(R.string.review_error_empty), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generate a random pastel color for the new avatar
            val avatarColors = listOf("#6D4C41", "#8D6E63", "#5D4037", "#4E342E", "#A1887F", "#795548")
            val randomColor = avatarColors.random()

            val newReview = com.android.ngopiyuk.model.Review(
                id = UUID.randomUUID().toString(),
                name = name,
                rating = rating,
                comment = comment,
                timeAgo = "Baru saja",
                avatarColor = randomColor
            )

            ReviewsManager.addReview(requireContext(), shop.id, newReview)
            
            // Dynamic UI refresh
            populateReviews(view, shop)

            Snackbar.make(view, getString(R.string.review_success), Snackbar.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}

