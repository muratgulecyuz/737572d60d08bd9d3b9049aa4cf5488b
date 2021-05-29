package com.example.unitedspacetraveler.network

import com.example.unitedspacetraveler.network.response.StationResponse
import retrofit2.http.GET

interface ServiceInterface {

    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getStations(): List<StationResponse>
}