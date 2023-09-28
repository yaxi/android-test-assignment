package com.example.shacklehotelbuddy.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class ReduxViewModel<S>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val mutex = Mutex()

    fun currentState(): S = _state.value

    val state: StateFlow<S>
        get() = _state.asStateFlow()

    suspend fun setState(reducer: S.() -> S) {
        mutex.withLock {
            _state.value = reducer(_state.value)
        }
    }

    fun CoroutineScope.launchSetState(dispatcher: CoroutineDispatcher, reducer: S.() -> S) {
        launch(dispatcher) { this@ReduxViewModel.setState(reducer) }
    }

    fun subscribe(dispatcher: CoroutineDispatcher, block: ((S) -> Unit)? = null) {
        viewModelScope.launch(dispatcher) {
            if (block == null) {
                _state.collect()
            } else {
                _state.collect { block(it) }
            }
        }
    }
}