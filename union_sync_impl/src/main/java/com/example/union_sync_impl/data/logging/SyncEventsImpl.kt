package com.example.union_sync_impl.data.logging

import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SyncEventsImpl : SyncEventsApi {

    private val eventsHolder = MutableSharedFlow<SyncEvent>()

    override suspend fun emit(syncEvent: SyncEvent) {
        eventsHolder.emit(syncEvent)
    }

    override fun subscribe(): Flow<SyncEvent> {
        return eventsHolder
    }
}