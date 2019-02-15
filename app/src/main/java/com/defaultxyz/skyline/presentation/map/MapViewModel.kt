package com.defaultxyz.skyline.presentation.map

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.extensions.or
import com.defaultxyz.skyline.utils.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : BaseViewModel() {
    val state = MutableLiveData<MapState>()
    var addLocationLatLng: LatLng? = null

    val locations = MutableLiveData<List<Location>>()

    fun loadLocations() {
        locationRepository.retrieveLocations()
            .subscribeBy(onError = {
                it.printStackTrace()
            }, onNext = {
                locations.postValue(it)
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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
    }
}