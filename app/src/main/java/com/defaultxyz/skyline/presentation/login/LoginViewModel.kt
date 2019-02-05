package com.defaultxyz.skyline.presentation.login

import androidx.lifecycle.*
import com.defaultxyz.skyline.domain.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), LifecycleObserver {
    private val compositeDisposable = CompositeDisposable()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginStatus = PublishSubject.create<Boolean>()

    fun onLoginAttempt() {
        (email.value to password.value).also { (email, password) ->
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                loginStatus.onError(IllegalArgumentException())
            } else {
                userRepository.sendLoginRequest(email, password)
                    .doOnError { loginStatus.onError(it) }
                    .subscribe({ user ->
                        loginStatus.onNext(user != null)
                    }, {
                        loginStatus.onError(it)
                    }).addTo(compositeDisposable)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.clear()
    }

}