package com.defaultxyz.skyline.utils

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.toDisposables() {
        compositeDisposable.add(this)
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy(source: LifecycleOwner) {
        compositeDisposable.clear()
    }

}