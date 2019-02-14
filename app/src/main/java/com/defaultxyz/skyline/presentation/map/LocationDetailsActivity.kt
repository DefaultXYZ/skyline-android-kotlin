package com.defaultxyz.skyline.presentation.map

import android.os.Bundle
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.presentation.map.adapter.ReviewAdapter
import com.defaultxyz.skyline.presentation.map.adapter.ReviewItem
import com.defaultxyz.skyline.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_location_details.*

const val LOCATION_KEY = "LocationKey"

class LocationDetailsActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<MapViewModel>(factory) }

    private val adapter by lazy { ReviewAdapter(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        setTitle(R.string.location_details)

        reviewList.adapter = adapter

        viewModel.locationReviews.observe(this, Observer {
            adapter.data = it.map(::ReviewItem)
        })
    }
}
