package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.sync.SyncRepository
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StarSyncRequestV2
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AllSyncImpl(
    private val syncControllerApi: SyncControllerApi,
    private val syncRepository: SyncRepository,
    private val coreDispatchers: CoreDispatchers,
    private val syncDao: NetworkSyncDao
) : AllSyncApi {

    override suspend fun syncAll() = withContext(coreDispatchers.io) {
        startNewSync()
    }

    private suspend fun startNewSync() {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dateTime: String = dateFormatter.format(Date(getLastSyncTime()))

        Timber.tag(SYNC_TAG).d("startNewSync date = $dateTime")
        val syncInfo = syncControllerApi.apiSyncPost(StarSyncRequestV2(dateTime))
        Timber.tag(SYNC_TAG).d("sync started ${syncInfo.id}")

        startExportFromLocalToServer(syncInfo.id)

        startExportFromServerToLocal(syncInfo.id)

        val syncCompletedInfo = syncControllerApi.apiSyncIdCompleteSyncPost(syncInfo.id)

        Timber.tag(SYNC_TAG).d(
            "sync completed\n" +
                    "isUploadComplete : ${syncCompletedInfo.importComplete}, uploaded parts count: ${syncCompletedInfo.importPartBufferInformation.importRequestsInformation?.size}\n" +
                    "isDownloadComplete : ${syncCompletedInfo.exportComplete}, all parts count from server: ${syncCompletedInfo.exportPartBufferInformation.exportPartsInformation?.size}"
        )
        updateLastSyncTime()
    }

    private suspend fun getLastSyncTime(): Long {
        return syncDao.getNetworkSync()?.lastSyncTime ?: 0
    }

    private suspend fun updateLastSyncTime() {
        syncDao.insert(
            NetworkSyncDb(
                lastSyncTime = System.currentTimeMillis(),
                isSynced = true
            )
        )
    }

    private suspend fun startExportFromLocalToServer(syncId: String) {
        syncControllerApi.apiSyncIdStartImportPost(syncId)
        Timber.tag(SYNC_TAG).d("started export from local to server")

        val syncEntities = syncRepository.getUploadSyncEntities()

        syncEntities.forEach { syncEntity ->
            syncEntity.upload(syncId)
        }
        syncControllerApi.apiSyncIdCompleteImportPost(syncId)

        Timber.tag(SYNC_TAG).d("completed export from local to server")
    }

    private suspend fun startExportFromServerToLocal(syncId: String) {
        val exportSyncInfo = syncControllerApi.apiSyncIdStartExportPost(syncId)
        Timber.tag(SYNC_TAG).d("started export from server to local")
        val syncEntities = syncRepository.getSyncEntities()

        val entityModelIds = hashSetOf<String>()

        exportSyncInfo.exportPartBufferInformation.exportPartsInformation.forEach { exportPartInformationV2 ->
            val exportPartId = exportPartInformationV2.id
            val entityId = exportPartInformationV2.entityModel.id
            val tableId = exportPartInformationV2.table

            entityModelIds.add(entityId)

            if (syncEntities.contains(entityId to tableId)) {
                val syncEntity = syncEntities.getOrElse(entityId to tableId) {
                    throw IllegalStateException()
                }
                syncEntity.exportFromServer(syncId, exportPartId)
            }
        }

        Timber.tag(SYNC_TAG).d("exportPartsInformation count ${exportSyncInfo.exportPartBufferInformation.exportPartsInformation.size}")
        Timber.tag(SYNC_TAG).d("all entityModelIds $entityModelIds in this sync")
        syncControllerApi.apiSyncIdCompleteExportPost(syncId)

        Timber.tag(SYNC_TAG).d("completed export from server to local")
    }

    companion object {
        const val SYNC_TAG = "SYNC_TAG"
    }
}