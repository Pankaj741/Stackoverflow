package com.example.stackoverflow.di.components

import android.content.Context
import com.example.stackoverflow.MyApplication
import com.example.stackoverflow.data.api.ApiHelper
import com.example.stackoverflow.data.api.ApiHelperImpl
import com.example.stackoverflow.data.api.ApiService
import com.example.stackoverflow.data.local.DatabaseService
import com.example.stackoverflow.di.modules.ApplicationModule
import com.example.stackoverflow.di.qualifier.ApplicationContext
import com.example.stackoverflow.di.qualifier.NetworkKey
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    fun getDatabaseService(): DatabaseService

    fun getApiService(): ApiService

    @NetworkKey
    fun getNetworkKey(): String

    @ApplicationContext
    fun getApplicationContext():  Context
}