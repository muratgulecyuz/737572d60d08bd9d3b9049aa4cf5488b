package com.example.unitedspacetraveler.ui.tabs.stations

import android.os.Bundle
import com.example.unitedspacetraveler.R
import com.example.unitedspacetraveler.base.BaseFragment
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationsFragment : BaseFragment(R.layout.fragment_stations) {
    private val stationsViewModel: StationsViewModel by viewModel()

    override fun prepareView(savedInstanceState: Bundle?) {
        observeStations()
    }

    private fun observeStations() {
        stationsViewModel.stationsResponse.observe(this, { response ->
            when (response.status) {
                STATUS_LOADING -> {

                }
                STATUS_ERROR -> {

                }
                STATUS_SUCCESS -> {
                }
            }
        })
    }
}