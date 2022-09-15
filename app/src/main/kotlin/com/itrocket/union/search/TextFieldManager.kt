package com.itrocket.union.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class TextFieldManager {
    private val textFieldQuery: MutableSharedFlow<String> = MutableSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    suspend fun listenSearch(): Flow<String> {
        return textFieldQuery.debounce(SEARCH_DELAY)
            .distinctUntilChanged()
            .flatMapLatest {
                flow {
                    this.emit(it)
                }
            }
    }

    suspend fun emit(query: String) {
        textFieldQuery.emit(query)
    }

    companion object {
        private const val SEARCH_DELAY = 300L
    }
}