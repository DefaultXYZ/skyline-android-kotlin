package com.defaultxyz.skyline.di

import com.defaultxyz.skyline.presentation.login.LoginFragment
import com.defaultxyz.skyline.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityContributorModule {

    @ContributesAndroidInjector
    fun bindsMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun bindsLoginFragment(): LoginFragment
}