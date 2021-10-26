package com.example.stackoverflow.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.stackoverflow.di.qualifier.ActivityContext
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: Fragment)  {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.requireContext()
}