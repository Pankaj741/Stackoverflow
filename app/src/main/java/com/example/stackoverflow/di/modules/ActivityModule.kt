package com.example.stackoverflow.di.modules

import android.app.Activity
import android.content.Context
import com.example.stackoverflow.di.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity)  {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity
}