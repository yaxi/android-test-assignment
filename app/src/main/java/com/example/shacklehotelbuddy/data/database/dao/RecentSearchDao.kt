package com.example.shacklehotelbuddy.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.shacklehotelbuddy.data.database.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: RecentSearchEntity): Long

    @Query("SELECT * FROM recent_searches")
    suspend fun getRecentSearches(): List<RecentSearchEntity>

    @Query("SELECT * FROM recent_searches")
    fun observeRecentSearches(): Flow<List<RecentSearchEntity>>

    @Update
    suspend fun update(entity: RecentSearchEntity)

    @Delete
    suspend fun delete(entity: RecentSearchEntity): Int
}