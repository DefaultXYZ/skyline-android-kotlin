package com.defaultxyz.skyline.presentation.map

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.ActivityMapBinding
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.latLng
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.utils.BaseActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : BaseActivity(), OnMapReadyCallback {
    private val viewModel by lazy { provideViewModel<MapViewModel>(factory) }

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMapBinding>(this, R.layout.activity_map).apply {
            viewModel = this@MapActivity.viewModel
            setLifecycleOwner(this@MapActivity)
        }
        lifecycle.addObserver(viewModel)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        viewModel.locations.observe(this, Observer { locations ->
            locations.map { it.marker() }.forEach { map.addMarker(it) }
        })
    }

    private fun Location.marker() = MarkerOptions()
        .position(latLng())
        .title(name)
}
