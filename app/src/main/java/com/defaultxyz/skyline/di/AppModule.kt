package com.defaultxyz.skyline.di

import android.app.Application
import android.content.Context
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.db.AppDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun providesAppContext(application: Application): Context = application.applicationContext

    @Provides
    fun providesDatabase(context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun providesApiClient(context: Context): ApiClient = Retrofit.Builder()
        .baseUrl(context.getString(R.string.url))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiClient::class.java)
}