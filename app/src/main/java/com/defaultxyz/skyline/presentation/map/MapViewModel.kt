package com.defaultxyz.skyline.presentation.map

import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.extensions.or
import com.defaultxyz.skyline.utils.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewModel @Inject constructor(
    locationRepository: LocationRepository
) : BaseViewModel() {
    val state = MutableLiveData<MapState>()
    val locations = mutableListOf<Location>()

    var addLocationLatLng: LatLng? = null

    init {
        locationRepository.retrieveLocations()
            .subscribeBy(onError = {
                it.printStackTrace()
            }, onNext = {
                locations.addAll(it)
                state.postValue(MapState.LOCATIONS)
            }).toDisposables()
    }

    fun onBackPressed() = state.value?.let { state ->
        when (state) {
            MapState.LOCATIONS -> false
            MapState.ADD_PLACE -> {
                this.state.postValue(MapState.LOCATIONS)
                true
            }
            MapState.ADD_PLACE_CONFIRM -> {
                this.state.postValue(MapState.ADD_PLACE)
                true
            }
        }
    }.or(false)
}