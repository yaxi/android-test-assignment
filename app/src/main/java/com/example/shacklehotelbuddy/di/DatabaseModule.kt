package com.example.shacklehotelbuddy.di

import android.content.Context
import androidx.room.Room
import com.example.shacklehotelbuddy.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val builder = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shackle-hotel-db"
        )

        return builder.build()
    }

    @Provides
    fun provideRecentSearchDao(db: AppDatabase) = db.getRecentSearchDao()
}