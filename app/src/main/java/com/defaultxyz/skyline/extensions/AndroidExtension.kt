package com.defaultxyz.skyline.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> AppCompatActivity.provideViewModel(factory: ViewModelProvider.Factory): T =
    ViewModelProviders.of(this, factory).get(T::class.java)