package com.example.shacklehotelbuddy.di

import com.example.shacklehotelbuddy.BuildConfig
import com.example.shacklehotelbuddy.Constants
import com.example.shacklehotelbuddy.data.network.ShackleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        if (!BuildConfig.DEBUG) {
            return null
        }

        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (loggingInterceptor != null) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideShackleService(client: OkHttpClient): ShackleService {
        return Retrofit.Builder()
            .baseUrl(Constants.Network.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShackleService::class.java)
    }
}