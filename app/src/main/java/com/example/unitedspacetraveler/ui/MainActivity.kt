package com.example.unitedspacetraveler.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseActivity
import com.example.unitedspacetraveler.databinding.ActivityMainBinding
import com.example.unitedspacetraveler.utils.viewBinding

class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navHostFragment: NavHostFragment
    override fun prepareView(savedInstanceState: Bundle?) {
        setUpNavigation()
    }

    override fun getRootView(): View {
        return binding.root
    }

    private fun setUpNavigation() {
        navHostFragment = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!

        NavigationUI.setupWithNavController(
            binding.bottomBar,
            navHostFragment.navController
        )
    }
}