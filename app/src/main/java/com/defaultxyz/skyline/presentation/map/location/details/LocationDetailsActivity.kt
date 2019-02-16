package com.defaultxyz.skyline.presentation.map.location.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.ActivityLocationDetailsBinding
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.extensions.showToast
import com.defaultxyz.skyline.presentation.map.adapter.ReviewAdapter
import com.defaultxyz.skyline.presentation.map.adapter.ReviewItem
import com.defaultxyz.skyline.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_location_details.*

const val LOCATION_KEY = "LocationKey"

class LocationDetailsActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<LocationDetailsViewModel>(factory) }

    private val adapter by lazy { ReviewAdapter(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLocationDetailsBinding>(
            this, R.layout.activity_location_details
        ).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        lifecycle.addObserver(viewModel)

        setTitle(R.string.location_details)

        intent.getParcelableExtra<Location>(LOCATION_KEY).apply {
            viewModel.locationCreator.postValue(userName)
            viewModel.location = this
        }

        reviewList.adapter = adapter

        viewModel.loadReviews()
        viewModel.locationReviews.observe(this, Observer {
            it.sumBy { review -> review.rating }
                .div(it.size.toFloat())
                .let { totalRating -> viewModel.placeRating.postValue(totalRating) }
            adapter.data = it.map(::ReviewItem)
        })

        viewModel.messageHandler.observe(this, Observer {
            showToast(it)
        })
    }
}
