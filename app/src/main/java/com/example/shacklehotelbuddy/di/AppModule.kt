package com.example.shacklehotelbuddy.di

import android.content.Context
import com.example.shacklehotelbuddy.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Named(DISPATCHER_DEFAULT)
    fun defaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

    @Provides
    @Named(DISPATCHER_IO)
    fun ioDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @Provides
    @Named(DISPATCHER_MAIN)
    fun mainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = CoroutineDispatchers(
        io = Dispatchers.IO,
        default = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    fun provideResources(@ApplicationContext context: Context) = context.resources

    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    companion object {
        const val DISPATCHER_DEFAULT = "DISPATCHER_DEFAULT"
        const val DISPATCHER_IO = "DISPATCHER_IO"
        const val DISPATCHER_MAIN = "DISPATCHER_MAIN"
    }
}