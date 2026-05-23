package com.android.ngopiyuk.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.ngopiyuk.fragment.CoffeeListFragment

class CoffeeCategoryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val categories = listOf("Semua", "Panas", "Dingin")

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        return CoffeeListFragment.newInstance(categories[position])
    }
}
