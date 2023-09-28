package com.example.shacklehotelbuddy.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shacklehotelbuddy.CoroutineDispatchers
import com.example.shacklehotelbuddy.search.model.DateData
import com.example.shacklehotelbuddy.search.state.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
): ReduxViewModel<SearchViewState>(SearchViewState()) {

    fun onCheckInPicked(dateInMillis: Long?) {
        dateInMillis?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = sdf.format(Date(dateInMillis))
            viewModelScope.launchSetState(dispatchers.main) {
                copy(checkInDate = date)
            }
        }
    }

    fun onCheckOutPicked(dateInMillis: Long?) {
        dateInMillis?.let {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = sdf.format(Date(dateInMillis))
            viewModelScope.launchSetState(dispatchers.main) {
                copy(checkOutDate = date)
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
}