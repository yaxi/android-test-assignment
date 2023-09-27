package com.example.shacklehotelbuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shacklehotelbuddy.data.database.dao.RecentSearchDao
import com.example.shacklehotelbuddy.data.database.entity.RecentSearchEntity

@Database(
    entities = [RecentSearchEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecentSearchDao(): RecentSearchDao
}
