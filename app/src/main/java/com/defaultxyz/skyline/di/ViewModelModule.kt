package com.defaultxyz.skyline.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.defaultxyz.skyline.di.annotation.ViewModelKey
import com.defaultxyz.skyline.presentation.login.LoginViewModel
import com.defaultxyz.skyline.presentation.main.MainViewModel
import com.defaultxyz.skyline.presentation.map.MapViewModel
import com.defaultxyz.skyline.presentation.map.location.add.AddLocationViewModel
import com.defaultxyz.skyline.presentation.map.location.details.LocationDetailsViewModel
import com.defaultxyz.skyline.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindsLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    fun bindsMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddLocationViewModel::class)
    fun bindsAddLocationViewModel(viewModel: AddLocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    fun bindsLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel
}