package com.vidstagramtest.injectors.component

import android.app.Application
import com.vidstagramtest.VidstagramApplication
import com.vidstagramtest.injectors.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (ViewModelModule::class), (NetworkModule::class), (ActivityModule::class), (FragmentModule::class), (UtilsModule::class), (RepositoryModule::class)]
)

interface AppComponent : AndroidInjector<VidstagramApplication> {

    override fun inject(instance: VidstagramApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}