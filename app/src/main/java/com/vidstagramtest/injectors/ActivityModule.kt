package com.vidstagramtest.injectors

import com.vidstagramtest.ui.view.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeBaseDaggerActivity(): BaseDaggerActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeCreateNewUserActivity(): NewUserActivity

    @ContributesAndroidInjector
    abstract fun contributePostActivity(): PostActivity

    @ContributesAndroidInjector
    abstract fun contributeNewPostActivity(): NewPostActivity

}