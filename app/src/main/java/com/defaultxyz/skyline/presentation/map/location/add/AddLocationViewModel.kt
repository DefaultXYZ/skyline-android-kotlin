package com.defaultxyz.skyline.presentation.map.location.add

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.utils.ActionResult
import com.defaultxyz.skyline.utils.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : BaseViewModel() {
    val placeName = MutableLiveData<String>()
    val placeReview = MutableLiveData<String>()
    val placeRating = MutableLiveData<Float>()
    val placeLocation = MutableLiveData<Pair<Double, Double>>()
    private var _placeName = ""
    private var _placeReview = ""
    private var _placeRating = 0
    private var _placeLocation = Pair(0.0, 0.0)

    val result = MutableLiveData<ActionResult<Location?>>()

    init {
        placeName.observeForever { _placeName = it }
        placeReview.observeForever { _placeReview = it }
        placeRating.observeForever { _placeRating = it.toInt() }
        placeLocation.observeForever { _placeLocation = it }
    }

    fun onAddLocation() {
        val location = Location(_placeName, _placeLocation.first, _placeLocation.second)
        val review = Review(_placeReview, _placeRating)
        locationRepository.addLocation(location, review)
            .subscribeBy(onError = {
                it.printStackTrace()
                result.postValue(ActionResult("Client error", null))
            }, onNext = {
                result.postValue(it)
            }).toDisposables()
    }

    override fun onDestroy(source: LifecycleOwner) {
        super.onDestroy(source)
        placeName.removeObservers(source)
        placeReview.removeObservers(source)
        placeLocation.removeObservers(source)
        placeRating.removeObservers(source)
    }
}