package com.defaultxyz.skyline.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesAppContext(application: Application): Context = application.applicationContext
}