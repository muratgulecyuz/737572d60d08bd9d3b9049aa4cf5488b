package com.example.unitedspacetraveler.di

import com.example.unitedspacetraveler.ui.tabs.stations.StationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single { NetworkModule().service() }
}

val viewModelModule = module {

    viewModel { StationsViewModel(get()) }
}