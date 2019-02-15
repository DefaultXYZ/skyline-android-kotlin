package com.defaultxyz.skyline.di

import com.defaultxyz.skyline.presentation.login.LoginActivity
import com.defaultxyz.skyline.presentation.login.LoginFragment
import com.defaultxyz.skyline.presentation.login.RegistrationFragment
import com.defaultxyz.skyline.presentation.main.MainActivity
import com.defaultxyz.skyline.presentation.map.location.add.AddLocationActivity
import com.defaultxyz.skyline.presentation.map.location.details.LocationDetailsActivity
import com.defaultxyz.skyline.presentation.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DependencyContributorModule {

    @ContributesAndroidInjector
    fun bindsMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindsLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    fun bindsLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun bindsRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    fun bindsMapActivity(): MapActivity

    @ContributesAndroidInjector
    fun bindsAddLocationActivity(): AddLocationActivity

    @ContributesAndroidInjector
    fun bindsLocationDetailsActivity(): LocationDetailsActivity
}