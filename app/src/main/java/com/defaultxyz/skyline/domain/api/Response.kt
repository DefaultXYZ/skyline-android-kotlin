package com.defaultxyz.skyline.domain.api

data class Response<T>(
    val message: String,
    val data: T?
)