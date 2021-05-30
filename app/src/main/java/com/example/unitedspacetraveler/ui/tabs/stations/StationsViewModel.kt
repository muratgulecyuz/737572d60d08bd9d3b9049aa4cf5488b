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
import com.example.unitedspacetraveler.utils.getDistance
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


    fun setFavorite(stationsDatabaseModel: StationsDatabaseModel) {
        stationsDatabaseModel.isFavorite = stationsDatabaseModel.isFavorite.not()
        updateStation(stationsDatabaseModel)
    }

    fun decreaseDamage() {
        mySpaceCraft.spaceCraftDamage -= 10
        updateSpaceCraft(mySpaceCraft)
    }

    private fun updateSpaceCraft(spaceCraftDatabaseModel: SpaceCraftDatabaseModel) =
        CoroutineScope(Dispatchers.IO).launch {
            spaceCraftDb.spaceCraftDao().updateSpaceCraft(spaceCraftDatabaseModel)
        }

    private fun updateStation(stationsDatabaseModel: StationsDatabaseModel) =
        CoroutineScope(Dispatchers.IO).launch {
            stationsDb.stationsDao().updateStation(stationsDatabaseModel)
        }

    private fun updateAllStations(stations: List<StationsDatabaseModel>) =
        CoroutineScope(Dispatchers.IO).launch {
            stationsDb.stationsDao().updateAllStations(stations)
        }

    fun travel(
        arrivedPlanet: StationsDatabaseModel,
        showToast: (message: String) -> Unit = {},
        setPlanetName: () -> Unit = {}
    ) {
        if (canGoPlanet(arrivedPlanet, showToast)) {
            setPlanetName.invoke()
            mySpaceCraft.xLocation = arrivedPlanet.coordinateX
            mySpaceCraft.yLocation = arrivedPlanet.coordinateY
            mySpaceCraft.spaceSuitNumber -= arrivedPlanet.need
            mySpaceCraft.universalSpaceTime -= arrivedPlanet.universalSpaceTime
            updateSpaceCraft(mySpaceCraft)

            adapterList.forEach {
                if (arrivedPlanet == it) {
                    it.universalSpaceTime = 0
                    it.need = 0
                    it.stock = it.capacity
                    it.isMissionCompleted = true
                } else {
                    it.universalSpaceTime = getDistance(
                        mySpaceCraft.xLocation.toInt(),
                        mySpaceCraft.yLocation.toInt(),
                        it.coordinateX.toInt(),
                        it.coordinateY.toInt()
                    )
                }

            }
            updateAllStations(adapterList)
        }

    }

    private fun canGoPlanet(
        arrivedPlanet: StationsDatabaseModel,
        showToast: (message: String) -> Unit
    ): Boolean {
        if (arrivedPlanet.need > mySpaceCraft.spaceSuitNumber) {
            showToast.invoke("Yeterli Giysi Yok")
            return false
        }
        if (arrivedPlanet.universalSpaceTime > mySpaceCraft.universalSpaceTime) {
            showToast.invoke("Yeterli Zaman Yok")
            return false
        }

        if (mySpaceCraft.spaceCraftDamage < 1) {
            showToast.invoke("Uzay aracı çok hasar aldı.")
            return false
        }
        return true
    }

    fun getCurrentPlanetName(): String {
        adapterList.forEach {
            if (it.universalSpaceTime == 0) {
                return "Şuan ${it.name} gezegenindesiniz."
            }
        }
        return ""
    }

    fun canGoSomePlanet(): Boolean {
        adapterList.filter { it.isMissionCompleted.not() }.forEach {
            if (it.universalSpaceTime <= mySpaceCraft.universalSpaceTime && it.need <= mySpaceCraft.spaceSuitNumber && mySpaceCraft.spaceCraftDamage > 0)
                return true
        }
        return false
    }
}