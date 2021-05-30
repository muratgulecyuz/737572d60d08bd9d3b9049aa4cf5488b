package com.example.unitedspacetraveler.ui.tabs.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitedspacetraveler.localdata.StationsDatabase
import com.example.unitedspacetraveler.localdata.StationsDatabaseModel

class FavoritesViewModel(val stationsDb: StationsDatabase) : ViewModel() {
    val adapterList = arrayListOf<StationsDatabaseModel>()
    var favoritesInfo: LiveData<List<StationsDatabaseModel>?> = MutableLiveData()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        favoritesInfo = stationsDb.stationsDao().getFavorites()
    }

    fun removeFavorite(id: Int) {
        stationsDb.stationsDao().setFavorite(false, id)
    }
}