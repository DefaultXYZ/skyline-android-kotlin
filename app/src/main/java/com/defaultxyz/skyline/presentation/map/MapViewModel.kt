package com.defaultxyz.skyline.presentation.map

import androidx.lifecycle.*
import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.extensions.or
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel(), LifecycleObserver {
    private val compositeDisposable = CompositeDisposable()

    val placeName = MutableLiveData<String>()
    val placeReview = MutableLiveData<String>()
    val placeRating = MutableLiveData<Int>()

    val locationCreator = MutableLiveData<String>()
    val locationReviews = MutableLiveData<List<Review>>()

    val state = MutableLiveData<MapState>()
    val locations = mutableListOf<Location>()

    init {
        locationRepository.retrieveLocations()
            .subscribeBy(onError = {
                it.printStackTrace()
            }, onNext = {
                locations.addAll(it)
                state.postValue(MapState.LOCATIONS)
            })
            .addTo(compositeDisposable)
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

    fun onAddLocation() {

    }

    fun onAddReview() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.clear()
    }

}