package com.example.shacklehotelbuddy.search.state

import android.app.appsearch.SearchResult
import com.example.shacklehotelbuddy.search.model.Property

data class SearchResultViewState(
    val isLoading: Boolean = false,
    val results: List<Property> = emptyList(),
    val error: Throwable? = null
)