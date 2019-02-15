package com.defaultxyz.skyline.presentation.login

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import com.defaultxyz.skyline.domain.UserRepository
import com.defaultxyz.skyline.domain.model.User
import com.defaultxyz.skyline.extensions.doOnNull
import com.defaultxyz.skyline.extensions.takeIfNotEmpty
import com.defaultxyz.skyline.utils.ActionResult
import com.defaultxyz.skyline.utils.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val dateOfBirth = MutableLiveData<Date>()
    val confirmPassword = MutableLiveData<String>()

    val resultMessage = MutableLiveData<ActionResult<LoginState>>()

    fun onLoginAttempt() {
        (email.value to password.value)
            .takeIfNotEmpty()
            ?.let { (email, password) ->
                userRepository.sendLoginRequest(email, password)
                    .subscribeBy(
                        onError = {
                            resultMessage.postValue(ActionResult("Client error", LoginState.FAILED))
                        }, onNext = {
                            resultMessage.postValue(it)
                        }
                    ).toDisposables()
            }.doOnNull {
                resultMessage.postValue(ActionResult("Enter data before login", LoginState.EMPTY))
            }
    }

    fun onRegistrationAttempt() {
        when {
            email.value.isNullOrEmpty() -> "Email is required field"
            password.value.isNullOrEmpty() -> "Password is required field"
            password.value != confirmPassword.value -> "Passwords do not match"
            else -> null
        }?.let { message ->
            resultMessage.postValue(ActionResult(message, LoginState.EMPTY))
        }.doOnNull {
            dateOfBirth.value.let { date ->
                date?.let { DateFormat.format("yyyy-MM-dd", date) }?.toString().orEmpty()
            }.let { date ->
                User(
                    email = email.value.orEmpty(),
                    password = password.value.orEmpty(),
                    firstName = firstName.value.orEmpty(),
                    lastName = lastName.value.orEmpty(),
                    dateOfBirth = date
                )
            }.let { user ->
                userRepository.sendRegistrationRequest(user)
                    .subscribeBy(
                        onError = {
                            resultMessage.postValue(ActionResult("Client error", LoginState.FAILED))
                        }, onNext = {
                            resultMessage.postValue(it)
                        }
                    ).toDisposables()
            }
        }
    }

}