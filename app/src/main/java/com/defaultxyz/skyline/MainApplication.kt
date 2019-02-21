package com.defaultxyz.skyline

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.defaultxyz.skyline.di.DaggerAppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerAppInjector.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector() = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

}