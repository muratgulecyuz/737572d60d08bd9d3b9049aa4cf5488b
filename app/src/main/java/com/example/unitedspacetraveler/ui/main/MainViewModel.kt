package com.example.unitedspacetraveler.ui.main

import androidx.lifecycle.ViewModel
import com.applogist.helpers.SingleLiveEvent
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabase
import com.example.unitedspacetraveler.localdata.StationsDatabase
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel
import com.example.unitedspacetraveler.network.ServiceInterface
import com.example.unitedspacetraveler.network.response.StationResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val service: ServiceInterface,
    val stationsDb: StationsDatabase,
) : ViewModel() {
    val stationsResponse: SingleLiveEvent<RESPONSE<List<StationResponse>>> = SingleLiveEvent()

    init {
        getStations()
        clearStations()
    }

    private fun getStations() {
        stationsResponse.request({ service.getStations() })
    }

    fun addStations(stations: List<StationResponse>) = CoroutineScope(Dispatchers.IO).launch {
        val stationsDbList = arrayListOf<StationsDatabaseModel>()
        stations.forEach {
            stationsDbList.add(
                StationsDatabaseModel(
                    name = it.name,
                    coordinateX = it.coordinateX,
                    coordinateY = it.coordinateY,
                    capacity = it.capacity,
                    stock = it.stock,
                    need = it.need,
                    isFavorite = false
                )
            )
        }
        stationsDb.stationsDao().insert(stationsDbList)
    }

    private fun clearStations() = CoroutineScope(Dispatchers.IO).launch {
        stationsDb.stationsDao().deleteStations()
    }

}