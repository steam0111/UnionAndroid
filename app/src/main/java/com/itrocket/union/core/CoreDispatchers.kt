package com.itrocket.union.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoreDispatchers {
    val io: CoroutineDispatcher
        get() = Dispatchers.IO

    val ui: CoroutineDispatcher
        get() = Dispatchers.Main
}