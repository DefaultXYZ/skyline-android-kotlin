package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.api.Response
import com.defaultxyz.skyline.domain.db.UserDao
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import com.defaultxyz.skyline.domain.db.entity.toEntity
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
    private val userDao: UserDao,
    private val apiClient: ApiClient
) {

    fun isUserAuthorized(): Single<User> = userDao.getUser().map(UserEntity::toUser)

    fun sendLoginRequest(email: String, password: String): Observable<ActionResult<LoginState>> =
        apiClient.login(email, password)
            .subscribeOn(Schedulers.io())
            .flatMap(::handleLoginResponse)

    fun sendRegistrationRequest(user: User): Observable<ActionResult<LoginState>> =
        apiClient.registerUser(user)
            .subscribeOn(Schedulers.io())
            .flatMap(::handleLoginResponse)

    private fun handleLoginResponse(response: Response<User>): Observable<ActionResult<LoginState>> =
        response.data?.let {
            userDao.insertUser(it.toEntity())
            LoginState.SUCCESS
        }.or(LoginState.FAILED).let {
            Observable.just(ActionResult(response.message, it))
        }

}