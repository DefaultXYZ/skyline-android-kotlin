package com.defaultxyz.skyline.presentation.map.location.details

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.ReviewRepository
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.extensions.doOnNull
import com.defaultxyz.skyline.extensions.takeIfNotEmpty
import com.defaultxyz.skyline.utils.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : BaseViewModel() {
    val locationCreator = MutableLiveData<String>()
    val locationReviews = MutableLiveData<List<Review>>()

    val placeRating = MutableLiveData<Float>()

    val placeReview = MutableLiveData<String>()
    private var _placeReview: String? = null

    val placeReviewRating = MutableLiveData<Float>()
    private var _placeReviewRating: Int? = null

    var location: Location? = null

    val messageHandler = MutableLiveData<String>()

    init {
        placeReview.observeForever { _placeReview = it }
        placeReviewRating.observeForever { _placeReviewRating = it.toInt() }
    }

    fun loadReviews() {
        location?.let { location ->
            reviewRepository.loadReviews(location)
                .subscribeBy(onError = {
                    it.printStackTrace()
                }, onNext = {
                    locationReviews.postValue(it)
                }).toDisposables()
        }
    }

    fun onAddReview() {
        when {
            _placeReview.isNullOrEmpty() -> "Review cannot be empty"
            _placeReviewRating == null -> "Rating should be set"
            location == null -> "Location is not available"
            else -> null
        }?.let {
            messageHandler.postValue(it)
        }.doOnNull {
            Triple(location, _placeReview, _placeReviewRating)
                .takeIfNotEmpty()
                ?.let { (location, review, rating) ->
                    reviewRepository.addReview(location, review, rating)
                        .subscribeBy(onError = {
                            it.printStackTrace()
                            messageHandler.postValue("Client error")
                        }, onNext = {
                            messageHandler.postValue(it.info)
                            placeReviewRating.postValue(0F)
                            placeReview.postValue("")
                            loadReviews()
                        }).toDisposables()
                }
        }
    }

    override fun onDestroy(source: LifecycleOwner) {
        super.onDestroy(source)
        placeReview.removeObservers(source)
        placeReviewRating.removeObservers(source)
    }
}