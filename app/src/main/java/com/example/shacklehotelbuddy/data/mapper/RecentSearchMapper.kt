package com.example.shacklehotelbuddy.data.mapper

import com.example.shacklehotelbuddy.search.model.RecentSearch
import com.example.shacklehotelbuddy.data.database.entity.RecentSearchEntity
import javax.inject.Inject

class RecentSearchMapper @Inject constructor() {

    fun mapEntityToModel(entity: RecentSearchEntity): RecentSearch {
        return RecentSearch(
            checkInDate = entity.checkInDate,
            checkOutDate = entity.checkOutDate,
            adults = entity.adults,
            children = entity.children
        )
    }
}