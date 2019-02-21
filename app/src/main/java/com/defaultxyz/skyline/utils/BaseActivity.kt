package com.defaultxyz.skyline.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.defaultxyz.skyline.R
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val toolbar: Toolbar? by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        toolbar?.apply { setSupportActionBar(this) }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun Disposable.addToDisposables() = this.addTo(compositeDisposable)
}