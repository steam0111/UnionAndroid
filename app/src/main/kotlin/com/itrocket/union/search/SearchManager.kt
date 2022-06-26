package com.itrocket.union.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SearchManager {
    val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    suspend fun listenSearch(onSearchChanged: suspend (String) -> Unit) {
        searchQuery.debounce(SEARCH_DELAY)
            .distinctUntilChanged()
            .flatMapLatest {
                flow {
                    this.emit(it)
                }
            }.collect {
                onSearchChanged(it)
            }
    }

    companion object {
        private const val SEARCH_DELAY = 300L
    }
}