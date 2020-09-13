package com.vidstagramtest

import com.vidstagramtest.injectors.component.DaggerAppComponent
import dagger.android.DaggerApplication

class VidstagramApplication : DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector() = applicationInjector

    override fun onCreate() {
        super.onCreate()
    }
}