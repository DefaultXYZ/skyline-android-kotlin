package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.db.AppDatabase
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import com.defaultxyz.skyline.domain.db.entity.toUser
import com.defaultxyz.skyline.domain.model.User
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val database: AppDatabase
) {

    fun isUserAuthorized(): Single<User> =
        database.userDao()
            .getUser()
            .map(UserEntity::toUser)

    fun sendLoginRequest(email: String, password: String): Observable<User> = TODO()

}