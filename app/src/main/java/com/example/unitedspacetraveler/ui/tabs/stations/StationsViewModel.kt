package com.example.unitedspacetraveler.ui.tabs.stations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabase
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabaseModel
import com.example.unitedspacetraveler.localdata.StationsDatabase
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel
import com.example.unitedspacetraveler.network.ServiceInterface
import com.example.unitedspacetraveler.network.response.StationResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import kotlinx.coroutines.*

class StationsViewModel(
    private val service: ServiceInterface,
    private val spaceCraftDb: SpaceCraftDatabase,
    private val stationsDb: StationsDatabase
) : ViewModel() {
    val stationsResponse: MutableLiveData<RESPONSE<List<StationResponse>>> = MutableLiveData()
    var spaceCraftInfo: LiveData<SpaceCraftDatabaseModel?> = MutableLiveData()
    var stationsInfo: LiveData<List<StationsDatabaseModel>?> = MutableLiveData()

    init {
        getStations()
        clearStations()
        getSpaceCraft()
        getStationsFromDb()
    }

    private fun getStations() {
        stationsResponse.request({ service.getStations() })
    }

    private fun getSpaceCraft() {
        spaceCraftInfo = spaceCraftDb.spaceCraftDao().getSpaceCraft()
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

    private fun getStationsFromDb() {
        stationsInfo = stationsDb.stationsDao().getStations()
    }

    private fun clearStations() = CoroutineScope(Dispatchers.IO).launch {
        stationsDb.stationsDao().deleteStations()
    }

    fun setFavorite(isFavorite: Boolean, id: Int) {
        stationsDb.stationsDao().setFavorite(isFavorite, id)
    }
}