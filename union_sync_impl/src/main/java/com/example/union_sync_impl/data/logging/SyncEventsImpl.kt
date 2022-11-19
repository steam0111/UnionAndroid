package com.example.union_sync_impl.data.logging

import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SyncEventsImpl : SyncEventsApi {

    private val eventsHolder = MutableSharedFlow<SyncEvent>()

    private val syncInfoHolder = MutableSharedFlow<SyncInfoType>()

    override suspend fun emitSyncEvent(syncEvent: SyncEvent) {
        eventsHolder.emit(syncEvent)
    }

    override fun subscribeSyncEvent(): Flow<SyncEvent> {
        return eventsHolder
    }

    override suspend fun emitSyncInfoType(syncInfoType: SyncInfoType) {
        syncInfoHolder.emit(syncInfoType)
    }

    override fun subscribeSyncInfoType(): Flow<SyncInfoType> {
        return syncInfoHolder
    }
}