package com.example.unitedspacetraveler.utils

import kotlin.math.sqrt

fun getDistance(
    currentXLocation: Int,
    currentYLocation: Int,
    arriveXLocation: Int,
    arriveYLocation: Int
): Int {
    val xDistance = (arriveXLocation - currentXLocation)
    val yDistance = (arriveYLocation - currentYLocation)
    val xSqr = (xDistance * xDistance).toDouble()
    val ySqr = (yDistance * yDistance).toDouble()
    return sqrt(xSqr + ySqr).toInt()
}