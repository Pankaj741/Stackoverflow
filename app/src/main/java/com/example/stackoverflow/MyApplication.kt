package com.example.stackoverflow

import android.app.Application
import com.example.stackoverflow.data.api.ApiHelper
import com.example.stackoverflow.data.api.ApiService
import com.example.stackoverflow.data.local.DatabaseService
import com.example.stackoverflow.di.components.ApplicationComponent
import com.example.stackoverflow.di.components.DaggerApplicationComponent
import com.example.stackoverflow.di.modules.ApplicationModule
import javax.inject.Inject

class MyApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var databaseService: DatabaseService

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}