package com.defaultxyz.skyline.presentation.map

import android.os.Bundle
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.utils.BaseActivity

class LocationDetailsActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<MapViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        setTitle(R.string.location_details)
    }
}
