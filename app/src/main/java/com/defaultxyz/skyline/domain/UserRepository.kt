package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.db.AppDatabase
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import com.defaultxyz.skyline.domain.db.entity.toUser
import com.defaultxyz.skyline.domain.model.User
import com.defaultxyz.skyline.extensions.or
import com.defaultxyz.skyline.presentation.login.LoginState
import com.defaultxyz.skyline.utils.ActionResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val database: AppDatabase,
    private val apiClient: ApiClient
) {

    fun isUserAuthorized(): Single<User> =
        database.userDao()
            .getUser()
            .map(UserEntity::toUser)

    fun sendLoginRequest(email: String, password: String): Observable<ActionResult<LoginState>> =
        apiClient.login(email, password)
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                response.data?.let {
                    // TODO: Insert user
                    LoginState.SUCCESS
                }.or(LoginState.FAILED).let {
                    Observable.just(ActionResult(response.message, it))
                }
            }

}