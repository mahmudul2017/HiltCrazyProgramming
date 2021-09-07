package com.hilt.crazyprogramming.dInjector

import android.content.Context
import android.content.SharedPreferences
import com.hilt.crazyprogramming.network.ApiService
import com.hilt.crazyprogramming.session.SharedPrefSession
import com.hilt.crazyprogramming.utlis.PREF_SHARED_SESSION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun providesBaseUrl(): String = "https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun providesRetrofitBuilder(baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_SHARED_SESSION, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesPreferenceSession(sharedPreferences: SharedPreferences): SharedPrefSession =
        SharedPrefSession(sharedPreferences)
}
