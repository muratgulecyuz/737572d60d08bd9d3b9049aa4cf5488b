package com.example.unitedspacetraveler.ui.tabs.stations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applogist.helpers.SingleLiveEvent
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
    private val spaceCraftDb: SpaceCraftDatabase,
    private val stationsDb: StationsDatabase
) : ViewModel() {
    var spaceCraftInfo: LiveData<SpaceCraftDatabaseModel?> = MutableLiveData()
    var stationsInfo: LiveData<List<StationsDatabaseModel>?> = MutableLiveData()
    val adapterList = arrayListOf<StationsDatabaseModel>()
    lateinit var mySpaceCraft: SpaceCraftDatabaseModel

    init {
        getSpaceCraft()
        getStationsFromDb()
    }


    private fun getSpaceCraft() {
        spaceCraftInfo = spaceCraftDb.spaceCraftDao().getSpaceCraft()
    }


    private fun getStationsFromDb() {
        stationsInfo = stationsDb.stationsDao().getStations()
    }


    fun setFavorite(isFavorite: Boolean, id: Int) {
        stationsDb.stationsDao().setFavorite(isFavorite, id)
    }

    fun decreaseDamage(currentDamage: Int) {
        spaceCraftDb.spaceCraftDao().decreaseDamage(currentDamage - 10)
    }
}