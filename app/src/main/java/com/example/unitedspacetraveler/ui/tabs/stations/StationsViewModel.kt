package com.example.unitedspacetraveler.ui.tabs.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitedspacetraveler.network.ServiceInterface
import com.example.unitedspacetraveler.network.response.StationResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request

class StationsViewModel(val service: ServiceInterface) : ViewModel() {
    val stationsResponse: MutableLiveData<RESPONSE<List<StationResponse>>> = MutableLiveData()

    init {
        getStations()
    }

    private fun getStations() {
        stationsResponse.request({ service.getStations() })
    }
}