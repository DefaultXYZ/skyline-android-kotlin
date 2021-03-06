package com.defaultxyz.skyline.presentation.map.location.add

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.ActivityAddLocationBinding
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.extensions.showToast
import com.defaultxyz.skyline.utils.BaseActivity
import com.google.android.gms.maps.model.LatLng

const val NEW_LOCATION_LAT_LNG_KEY = "AddLocationLatLngKey"

class AddLocationActivity : BaseActivity() {
    private val viewModel by lazy { provideViewModel<AddLocationViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAddLocationBinding>(
            this, R.layout.activity_add_location
        ).also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        lifecycle.addObserver(viewModel)

        setTitle(R.string.add_location)

        intent.getParcelableExtra<LatLng>(NEW_LOCATION_LAT_LNG_KEY)
            .destruct()
            .let {
                viewModel.placeLocation.postValue(it)
            }

        viewModel.result.observe(this, Observer {
            showToast(it.info)
            it.data?.let { finish() }
        })
    }

    private fun LatLng.destruct(): Pair<Double, Double> = Pair(latitude, longitude)
}
