package com.example.shacklehotelbuddy

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutineDispatchers(
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val main: CoroutineDispatcher
)