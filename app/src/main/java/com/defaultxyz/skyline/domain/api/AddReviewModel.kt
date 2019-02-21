package com.defaultxyz.skyline.domain.api

import com.defaultxyz.skyline.domain.model.Review

data class AddReviewModel(
    val text: String = "",
    val rating: Int = 0,
    val locationName: String = "",
    val userEmail: String = ""
)

fun AddReviewModel.toReview(): Review = Review(text, rating)