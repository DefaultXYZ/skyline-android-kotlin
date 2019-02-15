package com.defaultxyz.skyline.presentation.map.location.details

import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.ReviewRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.utils.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): BaseViewModel() {
    val locationCreator = MutableLiveData<String>()
    val locationReviews = MutableLiveData<List<Review>>()

    val placeRating = MutableLiveData<Float>()
    val placeReview = MutableLiveData<String>()

    fun loadReviews(location: Location) {
        reviewRepository.loadReviews(location)
            .subscribeBy(onError = {
                it.printStackTrace()
            }, onNext = {
                locationReviews.postValue(it)
            }).toDisposables()
    }

    fun onAddReview() {

    }
}