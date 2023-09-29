package com.example.shacklehotelbuddy.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.shacklehotelbuddy.util.Constants
import com.example.shacklehotelbuddy.util.CoroutineDispatchers
import com.example.shacklehotelbuddy.data.Resource
import com.example.shacklehotelbuddy.search.data.SearchRepository
import com.example.shacklehotelbuddy.search.state.SearchResultViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatchers: CoroutineDispatchers,
    private val searchRepository: SearchRepository,
): ReduxViewModel<SearchResultViewState>(
    SearchResultViewState()
) {

    private val checkInMillis: Long =
        savedStateHandle.get<String>(Constants.ParamKey.KEY_CHECKIN_MILLIS)!!.toLong()
    private val checkOutMillis: Long =
        savedStateHandle.get<String>(Constants.ParamKey.KEY_CHECKOUT_MILLIS)!!.toLong()
    private val adults: Int = savedStateHandle.get<String>(Constants.ParamKey.KEY_ADULTS)!!.toInt()
    private val children: Int =
        savedStateHandle.get<String>(Constants.ParamKey.KEY_CHILDREN)!!.toInt()

    init {
        search()
    }

    fun retry() {
        search()
    }

    private fun search() {
        viewModelScope.launch(dispatchers.io) {
            searchRepository.performSearch(
                checkInMillis,
                checkOutMillis,
                adults,
                children
            ).collect {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        viewModelScope.launchSetState(dispatchers.main) {
                            copy(isLoading = true, error = null)
                        }
                    }

                    Resource.Status.SUCCESS -> {
                        viewModelScope.launchSetState(dispatchers.main) {
                            copy(
                                isLoading = false,
                                results = it.data!!,
                                error = null
                            )
                        }
                    }

                    Resource.Status.ERROR -> {
                        viewModelScope.launchSetState(dispatchers.main) {
                            copy(
                                results = emptyList(),
                                isLoading = false,
                                error = it.error,
                            )
                        }
                    }
                }
            }
        }
    }
}