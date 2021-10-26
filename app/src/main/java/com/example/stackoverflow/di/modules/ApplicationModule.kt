package com.example.stackoverflow.di.modules

import android.content.Context
import androidx.room.Room
import com.example.stackoverflow.MyApplication
import com.example.stackoverflow.data.api.ApiService
import com.example.stackoverflow.data.api.RetrofitBuilder
import com.example.stackoverflow.data.local.DatabaseService
import com.example.stackoverflow.di.qualifier.ApplicationContext
import com.example.stackoverflow.di.qualifier.DatabaseName
import com.example.stackoverflow.di.qualifier.NetworkKey
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {

    @Singleton
    @Provides
    fun provideDatabaseService(): DatabaseService {
        return Room.databaseBuilder(
            application,
            DatabaseService::class.java,
            provideDatabaseName()
        ).build()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitBuilder.apiService
    }

    @ApplicationContext
    @Provides
    fun provideContext(): Context{
        return application
    }

    @NetworkKey
    @Provides
    fun provideNetworkKey(): String{
        return "ZiXCZbWaOwnDgpVT9Hx8IA(("
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String {
        return "question_db"
    }
}