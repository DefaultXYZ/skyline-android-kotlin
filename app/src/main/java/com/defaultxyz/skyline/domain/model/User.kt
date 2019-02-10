package com.defaultxyz.skyline.domain.model

data class User(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = ""
)