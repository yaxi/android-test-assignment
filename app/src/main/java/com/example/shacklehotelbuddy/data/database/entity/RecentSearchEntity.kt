package com.example.shacklehotelbuddy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "recent_searches"
)
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "check_in_date") val checkInDate: String,
    @ColumnInfo(name = "check_out_date") val checkOutDate: String,
    @ColumnInfo(name = "adults") val adults: Int,
    @ColumnInfo(name = "children") val children: Int,
)