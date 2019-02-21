package com.defaultxyz.skyline.domain.api

import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review

data class AddLocationModel(
    val location: Location,
    val review: Review
)