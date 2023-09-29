package com.example.shacklehotelbuddy.search.state

import com.example.shacklehotelbuddy.search.model.RecentSearch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SearchViewState(
    val checkInDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date()),
    val checkInDateInMillis: Long = Date().time,
    val checkOutDate: String = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.US
    ).format(Date().also { it.time += 24 * 60 * 60 * 1000 }),
    val checkOutDateInMillis: Long = Date().also { it.time += 24 * 60 * 60 * 1000 }.time,
    val adults: Int = 1,
    val children: Int = 0,
    val recentSearch: RecentSearch? = null
)

