package com.defaultxyz.skyline.presentation.map

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.ActivityMapBinding
import com.defaultxyz.skyline.extensions.addMarkers
import com.defaultxyz.skyline.extensions.provideViewModel
import com.defaultxyz.skyline.extensions.toggleVisibility
import com.defaultxyz.skyline.utils.BaseActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity(), OnMapReadyCallback {
    private val viewModel by lazy { provideViewModel<MapViewModel>(factory) }

    private var map: GoogleMap? = null

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

        viewModel.state.observe(this, Observer { it ->
            it?.apply {
                when (this) {
                    MapState.LOCATIONS -> {
                        addLocationButton.show()
                        map?.setOnMapLongClickListener(null)
                        map?.setOnMapClickListener {
                            addLocationButton.toggleVisibility()
                        }
                        map?.addMarkers(viewModel.locations)
                    }
                    MapState.ADD_PLACE -> {
                        addLocationButton.hide()
                        confirmLocationButton.hide()
                        map?.setOnMapClickListener(null)
                        map?.setOnMapLongClickListener {
                            viewModel.state.postValue(MapState.ADD_PLACE_CONFIRM)
                            map?.clear()
                            map?.addMarker(MarkerOptions().position(it))
                        }
                        map?.clear()
                    }
                    MapState.ADD_PLACE_CONFIRM -> {
                        confirmLocationButton.show()
                    }
                }
            }
        })

        addLocationButton.setOnClickListener {
            viewModel.state.postValue(MapState.ADD_PLACE)
        }

        confirmLocationButton.setOnClickListener {

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onBackPressed() {
        if (!viewModel.onBackPressed()) super.onBackPressed()
    }
}
