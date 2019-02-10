package com.defaultxyz.skyline.presentation.login

import androidx.lifecycle.*
import com.defaultxyz.skyline.domain.UserRepository
import com.defaultxyz.skyline.extensions.doOnNull
import com.defaultxyz.skyline.extensions.takeIfNotEmpty
import com.defaultxyz.skyline.utils.ActionResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), LifecycleObserver {
    private val compositeDisposable = CompositeDisposable()

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
                    ).addTo(compositeDisposable)
            }.doOnNull {
                ActionResult("Enter data before login", LoginState.EMPTY).apply {
                    resultMessage.postValue(this)
                }
            }
    }

    fun onRegistrationAttempt() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.clear()
    }

}