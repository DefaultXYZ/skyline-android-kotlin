package com.defaultxyz.skyline.presentation.main

import android.os.Bundle
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.extensions.doOnNull
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.extensions.showToast
import com.defaultxyz.skyline.extensions.startActivity
import com.defaultxyz.skyline.presentation.login.LoginActivity
import com.defaultxyz.skyline.presentation.map.MapActivity
import com.defaultxyz.skyline.utils.BaseActivity
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<MainViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchUser()
            .subscribeBy(
                onError = {
                    startActivity<LoginActivity>(true)
                },
                onSuccess = {
                    it.data?.let { user ->
                        showToast("Welcome back, ${user.firstName}!")
                        startActivity<MapActivity>(true)
                    }.doOnNull {
                        startActivity<LoginActivity>(true)
                    }
                })
            .addToDisposables()
    }
}
