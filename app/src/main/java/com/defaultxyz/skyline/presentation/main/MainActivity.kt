package com.defaultxyz.skyline.presentation.main

import android.os.Bundle
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.utils.BaseActivity

class MainActivity : BaseActivity() {

    val viewModel by lazy { provideViewModel<MainViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
