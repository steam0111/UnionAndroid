package com.itrocket.union.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchManager {
    private val searchQuery: MutableStateFlow<SearchEvent> = MutableStateFlow(SearchEvent.FirstEmit)

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    suspend fun listenSearch(onSearchChanged: suspend (String) -> Unit) {
        searchQuery.debounce {
            when (it) {
                SearchEvent.FirstEmit -> 0
                is SearchEvent.OnSearchChanged -> SEARCH_DELAY
            }
        }
            .map {
                when (it) {
                    SearchEvent.FirstEmit -> it
                    is SearchEvent.OnSearchChanged -> SearchEvent.OnSearchChanged(it.value.trim())
                }
            }
            .distinctUntilChanged()
            .flatMapLatest {
                flow {
                    this.emit(it)
                }
            }
            .collect {
                onSearchChanged(
                    when (it) {
                        SearchEvent.FirstEmit -> ""
                        is SearchEvent.OnSearchChanged -> it.value
                    }
                )
            }
    }

    suspend fun emit(query: String) {
        searchQuery.emit(SearchEvent.OnSearchChanged(query))
    }

    companion object {
        private const val SEARCH_DELAY = 300L
    }
}

sealed class SearchEvent {
    object FirstEmit : SearchEvent()
    data class OnSearchChanged(val value: String) : SearchEvent()
}