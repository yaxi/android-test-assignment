package com.example.shacklehotelbuddy.search

import androidx.lifecycle.viewModelScope
import com.example.shacklehotelbuddy.util.CoroutineDispatchers
import com.example.shacklehotelbuddy.search.data.SearchRepository
import com.example.shacklehotelbuddy.search.state.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val repository: SearchRepository,
): ReduxViewModel<SearchViewState>(SearchViewState()) {

    init {
        viewModelScope.launch(dispatchers.default) {
            repository.observeRecentSearches()
                .collect {
                    viewModelScope.launchSetState(dispatchers.main) {
                        if (it.isEmpty()) {
                            copy(recentSearch = null)
                        } else {
                            copy(recentSearch = it.last())
                        }
                    }
                }
        }
    }

    fun onCheckInPicked(dateInMillis: Long?) {
        dateInMillis?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = sdf.format(Date(dateInMillis))
            viewModelScope.launchSetState(dispatchers.main) {
                copy(checkInDate = date, checkInDateInMillis = it)
            }
        }
    }

    fun onCheckOutPicked(dateInMillis: Long?) {
        dateInMillis?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = sdf.format(Date(dateInMillis))
            viewModelScope.launchSetState(dispatchers.main) {
                copy(checkOutDate = date, checkOutDateInMillis = it)
            }
        }
    }

    fun onAdultChanged(num: Int) {
        viewModelScope.launchSetState(dispatchers.main) {
            copy(adults = num)
        }
    }

    fun onChildrenChanged(num: Int) {
        viewModelScope.launchSetState(dispatchers.main) {
            copy(children = num)
        }
    }

    fun onSearchClicked() {
        // save current search criteria
        val state = currentState()
        viewModelScope.launch(dispatchers.io) {
            with(state) {
                repository.saveLastSearch(
                    checkInDate = this.checkInDate,
                    checkOutDate = this.checkOutDate,
                    adults = this.adults,
                    children = this.children
                )
            }
        }
    }
}