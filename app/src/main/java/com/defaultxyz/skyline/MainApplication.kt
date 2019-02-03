package com.defaultxyz.skyline

import android.app.Activity
import android.app.Application
import com.defaultxyz.skyline.di.DaggerAppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppInjector.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector() = activityInjector

}