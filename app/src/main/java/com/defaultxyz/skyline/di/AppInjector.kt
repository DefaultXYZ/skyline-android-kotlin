package com.defaultxyz.skyline.di

import android.app.Application
import com.defaultxyz.skyline.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DependencyContributorModule::class,
        ViewModelModule::class
    ]
)
interface AppInjector {

    fun inject(application: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppInjector
    }
}