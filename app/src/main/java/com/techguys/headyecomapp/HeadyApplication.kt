package com.techguys.headyecomapp

import android.app.Application
import com.techguys.headyecomapp.di.AppModule
import com.techguys.headyecomapp.di.AppComponent
import com.techguys.headyecomapp.di.DaggerAppComponent


class HeadyApplication: Application() {

    lateinit var appComp: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComp = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }

}