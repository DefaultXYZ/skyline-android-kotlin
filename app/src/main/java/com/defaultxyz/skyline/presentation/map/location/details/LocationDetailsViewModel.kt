package com.defaultxyz.skyline.presentation.map.location.details

import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.utils.BaseViewModel
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(): BaseViewModel() {
    val locationCreator = MutableLiveData<String>()
    val locationReviews = MutableLiveData<List<Review>>()

    val placeReview = MutableLiveData<String>()

    fun onAddReview() {

    }
}