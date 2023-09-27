package com.example.shacklehotelbuddy.search.state

import com.example.shacklehotelbuddy.search.model.DateData

data class SearchViewState(
    val checkInDate: DateData = DateData.default(),
    val checkOutDate: DateData = DateData.default(),
    val adults: Int = 1,
    val children: Int = 0
)

