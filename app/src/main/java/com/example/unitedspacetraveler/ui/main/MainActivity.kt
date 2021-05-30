package com.example.unitedspacetraveler.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseActivity
import com.example.unitedspacetraveler.databinding.ActivityMainBinding
import com.example.unitedspacetraveler.utils.viewBinding
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel: MainViewModel by viewModel()

    override fun prepareView(savedInstanceState: Bundle?) {
        setUpNavigation()
        observeStations()
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

    private fun observeStations() {
        viewModel.stationsResponse.observe(this, { response ->
            when (response.status) {
                STATUS_LOADING -> {

                }
                STATUS_ERROR -> {

                }
                STATUS_SUCCESS -> {
                    response.responseObject?.let { viewModel.addStations(it) }
                }
            }
        })
    }
}