package com.example.unitedspacetraveler.network.response

data class StationResponse(
    val name: String,
    val coordinateX: Double,
    val coordinateY: Double,
    val capacity: Int,
    val stock: Int,
    val need: Int
)