package com.techguys.headyecomapp.di

import com.techguys.headyecomapp.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{

    fun inject(splashActivity: SplashActivity)

}