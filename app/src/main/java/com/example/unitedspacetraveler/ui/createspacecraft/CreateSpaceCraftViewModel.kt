package com.example.unitedspacetraveler.ui.createspacecraft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabase
import com.example.unitedspacetraveler.localdata.SpaceCraftDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateSpaceCraftViewModel(private val db: SpaceCraftDatabase) : ViewModel() {

    var createSpaceDraftResponse: LiveData<SpaceCraftDatabaseModel?> = MutableLiveData()

    init {
        getSpaceCraft()
    }

    fun addSpaceCraft(spaceCraftDatabase: SpaceCraftDatabaseModel) =
        CoroutineScope(Dispatchers.IO).launch {
            db.spaceCraftDao().insert(spaceCraftDatabase)
        }

    fun clearSpaceCraft() = CoroutineScope(Dispatchers.IO).launch {
        db.spaceCraftDao().deleteCraft()
    }

    fun getSpaceCraft() {
        createSpaceDraftResponse = db.spaceCraftDao().getSpaceCraft()
    }
}