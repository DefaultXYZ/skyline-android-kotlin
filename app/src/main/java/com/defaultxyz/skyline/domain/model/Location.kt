package com.defaultxyz.skyline.domain.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val userName: String = ""
) : Parcelable

fun Location.latLng(): LatLng = LatLng(latitude, longitude)