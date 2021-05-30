package com.example.unitedspacetraveler.ui.tabs.favorites

import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseFragment
import com.example.unitedspacetraveler.databinding.FragmentFavoritesBinding

class FavoritesFragment(override val layoutId: Int = R.layout.fragment_favorites) : BaseFragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun setUpBinding() {
        binding = bBinding as FragmentFavoritesBinding
    }
}