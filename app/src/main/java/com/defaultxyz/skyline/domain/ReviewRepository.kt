package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val apiClient: ApiClient
) {

    fun loadReviews(location: Location): Observable<List<Review>> =
        apiClient.getReviews(location.name)
            .subscribeOn(Schedulers.io())
            .map { it.data.orEmpty() }
}