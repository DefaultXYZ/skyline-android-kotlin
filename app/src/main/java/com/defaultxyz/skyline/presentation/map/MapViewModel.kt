package com.defaultxyz.skyline.presentation.map

import androidx.lifecycle.*
import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.model.Location
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel(), LifecycleObserver {
    private val compositeDisposable = CompositeDisposable()
    val locations = MutableLiveData<List<Location>>()

    init {
        locationRepository.retrieveLocations()
            .subscribeBy { locations.postValue(it) }
            .addTo(compositeDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.clear()
    }

}