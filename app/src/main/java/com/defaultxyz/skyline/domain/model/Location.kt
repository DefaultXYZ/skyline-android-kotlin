package com.defaultxyz.skyline.domain.model

import com.google.android.gms.maps.model.LatLng

data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

fun Location.latLng(): LatLng = LatLng(latitude, longitude)