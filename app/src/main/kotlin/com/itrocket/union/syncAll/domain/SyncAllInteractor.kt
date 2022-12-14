package com.itrocket.union.syncAll.domain

import android.content.Context
import com.example.union_sync_api.entity.SyncDirection
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import com.itrocket.union.authMain.domain.dependencies.AuthMainRepository
import com.itrocket.union.syncAll.domain.dependencies.SyncAllRepository
import com.itrocket.union.utils.getFullDateFromMillis
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SyncAllInteractor(
    private val repository: SyncAllRepository,
    private val authMainRepository: AuthMainRepository,
    private val coreDispatchers: CoreDispatchers,
    private val applicationContext: Context
) {
    suspend fun getLastSyncDate(): String {
        val lastSyncTime = repository.getLastSyncTime()
        return if (lastSyncTime == 0L) {
            ""
        } else {
            getFullDateFromMillis(lastSyncTime)
        }
    }

    suspend fun updateMyConfig() {
        withContext(coreDispatchers.io) {
            val config = authMainRepository.getMyConfig()
            authMainRepository.saveMyConfig(config)
        }
    }

    suspend fun syncAll() = withContext(coreDispatchers.io) {
        updateMyConfig()
        repository.syncAll()
    }

    suspend fun clearAll() =
        withContext(coreDispatchers.io) {
            repository.clearAll()
        }

    fun subscribeSyncEvents(): Flow<SyncEvent> {
        return repository.subscribeSyncEvents()
    }

    fun subscribeSyncInfo(): Flow<SyncInfoType> {
        return repository.subscribeSyncInfoType()
    }

    fun getSyncTitle(syncInfoType: SyncInfoType): String {
        return when (syncInfoType) {
            is SyncInfoType.ItemCount -> ""
            is SyncInfoType.TitleEvent -> syncInfoType.title
            is SyncInfoType.TitleResourceEvent -> {
                val tableName = applicationContext.getString(syncInfoType.titleId)
                when (syncInfoType.syncDirection) {
                    SyncDirection.FROM_SERVER -> applicationContext.getString(
                        R.string.sync_all_download_current_table,
                        tableName
                    )
                    SyncDirection.TO_SERVER -> applicationContext.getString(
                        R.string.sync_all_upload_current_table,
                        tableName
                    )
                }
            }
        }
    }

    fun getItemCount(itemCount: SyncInfoType.ItemCount, syncedCount: Long): Long {
        val count = itemCount.count
        return if (count != null) {
            syncedCount + count
        } else {
            0
        }
    }

    suspend fun isSyncEventHasError(events: List<SyncEvent>): Boolean {
        return withContext(coreDispatchers.io) {
            events.any { it is SyncEvent.Error }
        }
    }

    suspend fun addNewEvent(events: List<SyncEvent>, newEvent: SyncEvent): List<SyncEvent> {
        return withContext(coreDispatchers.io) {
            val mutableEvents = events.toMutableList()
            mutableEvents.add(0, newEvent)
            mutableEvents
        }
    }
}