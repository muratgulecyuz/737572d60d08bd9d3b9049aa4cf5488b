package com.example.unitedspacetraveler.di

import com.example.unitedspacetraveler.localdata.SpaceCraftDatabase
import com.example.unitedspacetraveler.localdata.StationsDatabase
import com.example.unitedspacetraveler.ui.createspacecraft.CreateSpaceCraftViewModel
import com.example.unitedspacetraveler.ui.tabs.stations.StationsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single { NetworkModule().service() }
    single { SpaceCraftDatabase.create(androidContext()) }
    single { StationsDatabase.create(androidContext()) }
}

val viewModelModule = module {

    viewModel { StationsViewModel(get(), get(), get()) }
    viewModel { CreateSpaceCraftViewModel(get()) }
}