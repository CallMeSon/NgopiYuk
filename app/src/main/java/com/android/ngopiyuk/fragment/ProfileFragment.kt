package com.android.ngopiyuk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.ngopiyuk.R
import com.android.ngopiyuk.adapter.Voucher
import com.android.ngopiyuk.adapter.VoucherAdapter
import com.android.ngopiyuk.utils.FavoritesManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    companion object {
        private const val PREF_PROFILE = "ngopiyuk_profile"
        private const val KEY_NAME = "profile_name"
        private const val KEY_EMAIL = "profile_email"
        private const val KEY_PHONE = "profile_phone"
    }

    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvProfilePhone: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI Elements
        tvProfileName = view.findViewById(R.id.tvProfileName)
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail)
        tvProfilePhone = view.findViewById(R.id.tvProfilePhone)

        // Bind top profile card data
        loadProfileData()

        // Setup ViewPager2 and TabLayout
        val tabLayout: TabLayout = view.findViewById(R.id.profileTabLayout)
        val viewPager: ViewPager2 = view.findViewById(R.id.profileViewPager)

        viewPager.adapter = ProfileTabAdapter(requireContext(), 
            onFavoritesClick = { findNavController().navigate(R.id.favoritesFragment) },
            onSettingsClick = { findNavController().navigate(R.id.settingsFragment) },
            onAboutClick = { findNavController().navigate(R.id.aboutFragment) }
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Informasi"
                else -> "Voucher Saya"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        loadProfileData()
        
        // Reload statistics bookmark count when returning back to profile screen
        val viewPager: ViewPager2? = view?.findViewById(R.id.profileViewPager)
        viewPager?.adapter?.notifyDataSetChanged()
    }

    private fun loadProfileData() {
        if (!isAdded) return

        val prefs = requireContext().getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_NAME, "").orEmpty().trim()
        val email = prefs.getString(KEY_EMAIL, "").orEmpty().trim()
        val phone = prefs.getString(KEY_PHONE, "").orEmpty().trim()

        tvProfileName.text = name.ifEmpty { getString(R.string.profile_default_name) }
        tvProfileEmail.text = email.ifEmpty { getString(R.string.profile_default_email) }
        tvProfilePhone.text = phone.ifEmpty { getString(R.string.profile_default_phone) }
    }

    // View-based ViewPager2 adapter to cleanly map tabs
    private class ProfileTabAdapter(
        private val context: Context,
        private val onFavoritesClick: () -> Unit,
        private val onSettingsClick: () -> Unit,
        private val onAboutClick: () -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        class InfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvBookmarksCount: TextView = view.findViewById(R.id.tvBookmarksCount)
            val layoutMenuFavorites: View = view.findViewById(R.id.layoutMenuFavorites)
            val layoutMenuSettings: View = view.findViewById(R.id.layoutMenuSettings)
            val layoutMenuAbout: View = view.findViewById(R.id.layoutMenuAbout)
        }

        class VouchersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val rvProfileVouchers: RecyclerView = view.findViewById(R.id.rvProfileVouchers)
        }

        override fun getItemViewType(position: Int): Int = position

        override fun getItemCount(): Int = 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                0 -> {
                    val view = inflater.inflate(R.layout.profile_tab_info, parent, false)
                    InfoViewHolder(view)
                }
                else -> {
                    val view = inflater.inflate(R.layout.profile_tab_vouchers, parent, false)
                    VouchersViewHolder(view)
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is InfoViewHolder) {
                // Fetch dynamic bookmark count from FavoritesManager
                val favoriteIdsCount = FavoritesManager.getFavoriteIds(context).size
                holder.tvBookmarksCount.text = favoriteIdsCount.toString()

                // Bind clicks for navigation
                holder.layoutMenuFavorites.setOnClickListener { onFavoritesClick() }
                holder.layoutMenuSettings.setOnClickListener { onSettingsClick() }
                holder.layoutMenuAbout.setOnClickListener { onAboutClick() }
            } else if (holder is VouchersViewHolder) {
                // Load dummy vouchers recycler view inside the Profile swipeable tab
                val dummyVouchers = listOf(
                    Voucher(1, "20%", "Diskon 20% di Roast & Revel", "Berlaku hingga 30 Jun 2026"),
                    Voucher(2, "15%", "Diskon 15% untuk Cold Brew di Urban Brew", "Berlaku hingga 15 Jul 2026"),
                    Voucher(3, "BUY1", "Buy 1 Get 1 di Bean & Beyond", "Berlaku hingga 20 Jun 2026"),
                    Voucher(4, "10%", "Diskon 10% semua menu di Kopi Nusantara", "Berlaku hingga 31 Jul 2026"),
                    Voucher(5, "FREE", "Gratis Pastry untuk pembelian kopi di WorkBrew Hub", "Berlaku hingga 25 Jun 2026"),
                    Voucher(6, "25%", "Diskon 25% Weekend Special di Rustic Grinder", "Berlaku hingga 30 Jun 2026")
                )
                holder.rvProfileVouchers.layoutManager = LinearLayoutManager(context)
                holder.rvProfileVouchers.adapter = VoucherAdapter(dummyVouchers)
            }
        }
    }
}
