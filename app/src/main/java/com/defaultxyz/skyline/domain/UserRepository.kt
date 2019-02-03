package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.db.AppDatabase
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import com.defaultxyz.skyline.domain.db.entity.toUser
import com.defaultxyz.skyline.domain.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val database: AppDatabase
) {

    fun isUserAuthorized(): Single<User> =
        database.userDao()
            .getUser()
            .map(UserEntity::toUser)

}