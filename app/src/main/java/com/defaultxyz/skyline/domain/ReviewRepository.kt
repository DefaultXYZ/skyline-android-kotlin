package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.api.AddReviewModel
import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.api.toReview
import com.defaultxyz.skyline.domain.db.UserDao
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.utils.ActionResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiClient: ApiClient
) {

    fun loadReviews(location: Location): Observable<List<Review>> =
        apiClient.getReviews(location.name)
            .subscribeOn(Schedulers.io())
            .map { it.data.orEmpty() }

    fun addReview(location: Location, text: String, rating: Int): Observable<ActionResult<Review?>> =
        userDao.getUser()
            .subscribeOn(Schedulers.io())
            .flatMapObservable {
                AddReviewModel(text, rating, location.name, it.email).let { review ->
                    apiClient.addReview(review)
                        .map { response ->
                            ActionResult(response.message, response.data?.toReview())
                        }
                }
            }
}