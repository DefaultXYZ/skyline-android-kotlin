package com.defaultxyz.skyline.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.defaultxyz.skyline.di.annotation.ViewModelKey
import com.defaultxyz.skyline.presentation.main.MainViewModel
import com.defaultxyz.skyline.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}