package com.defaultxyz.skyline.di

import android.app.Application
import android.content.Context
import com.defaultxyz.skyline.domain.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providesDatabase(context: Context) = AppDatabase.getInstance(context)
}