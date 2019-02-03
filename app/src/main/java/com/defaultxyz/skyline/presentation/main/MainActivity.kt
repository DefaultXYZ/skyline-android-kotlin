package com.defaultxyz.skyline.presentation.main

import android.os.Bundle
import android.widget.Toast
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.utils.BaseActivity

class MainActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<MainViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchUser()
            .subscribe({
                displayMessage("Welcome back, ${it.firstName}!")
            }, {
                displayMessage("Please login or sign up")
            })
            .addToDisposables()
    }

    private fun displayMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
