package com.defaultxyz.skyline.presentation.map

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.ActivityMapBinding
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.extensions.*
import com.defaultxyz.skyline.presentation.map.location.add.AddLocationActivity
import com.defaultxyz.skyline.presentation.map.location.add.NEW_LOCATION_LAT_LNG_KEY
import com.defaultxyz.skyline.presentation.map.location.details.LOCATION_KEY
import com.defaultxyz.skyline.presentation.map.location.details.LocationDetailsActivity
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
            lifecycleOwner = this@MapActivity
        }
        lifecycle.addObserver(viewModel)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.state.observe(this, Observer { state ->
            state?.apply {
                when (this) {
                    MapState.LOCATIONS -> {
                        addLocationButton.show()
                        map?.setOnMapLongClickListener(null)
                        map?.setOnInfoWindowClickListener {
                            addLocationButton.toggleVisibility()
                        }
                        map?.setOnInfoWindowClickListener {
                            (it.tag as? Location)?.also { location ->
                                startActivity<LocationDetailsActivity> {
                                    putExtra(LOCATION_KEY, location)
                                }
                            }
                        }
                        viewModel.locations.value?.let {
                            map?.addMarkers(it)
                        }
                    }
                    MapState.ADD_PLACE -> {
                        addLocationButton.hide()
                        confirmLocationButton.hide()
                        map?.setOnMapClickListener(null)
                        map?.setOnMapLongClickListener {
                            viewModel.addLocationLatLng = it

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

        viewModel.locations.observe(this, Observer {
            if (viewModel.state.value == MapState.LOCATIONS) {
                map?.addMarkers(it)
            }
        })

        addLocationButton.setOnClickListener {
            showToast("Choose location to add")
            viewModel.state.postValue(MapState.ADD_PLACE)
        }

        confirmLocationButton.setOnClickListener {
            startActivity<AddLocationActivity> {
                putExtra(NEW_LOCATION_LAT_LNG_KEY, viewModel.addLocationLatLng)
            }
            viewModel.state.postValue(MapState.LOCATIONS)
        }

        viewModel.state.postValue(MapState.LOCATIONS)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLocations()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onBackPressed() {
        if (!viewModel.onBackPressed()) super.onBackPressed()
    }
}
