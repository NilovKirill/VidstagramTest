package com.vidstagramtest.injectors

import com.vidstagramtest.ui.view.BaseDaggerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeBaseDaggerFragment(): BaseDaggerFragment
}