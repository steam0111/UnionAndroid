package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.sync.SyncRepository
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StarSyncRequestV2
import org.openapitools.client.models.SyncInformationV2
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
class AllSyncImpl(
    private val syncControllerApi: SyncControllerApi,
    private val syncRepository: SyncRepository,
    private val coreDispatchers: CoreDispatchers,
    private val syncDao: NetworkSyncDao,
    private val syncEventsApi: SyncEventsApi
) : AllSyncApi {

    override suspend fun syncAll() = withContext(coreDispatchers.io) {
        startNewSync()
    }

    private suspend fun startNewSync() {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dateTime: String = dateFormatter.format(Date(getLastSyncTime()))

        Timber.tag(SYNC_TAG).d("startNewSync date = $dateTime")

        syncEventsApi.emit(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт синхронизации, время: $dateTime"
            )
        )

        val duration = measureTime {
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

        syncEventsApi.emit(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Синхронизация завершена",
                duration = duration
            )
        )
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
        syncEventsApi.emit(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт выгрузки на сервер"
            )
        )

        val duration = measureTime {
            syncControllerApi.apiSyncIdStartImportPost(syncId)
            Timber.tag(SYNC_TAG).d("started export from local to server")

            val syncEntities = syncRepository.getUploadSyncEntities()

            syncEntities.forEach { syncEntity ->
                syncEntity.upload(syncId)
            }
            syncControllerApi.apiSyncIdCompleteImportPost(syncId)

            Timber.tag(SYNC_TAG).d("completed export from local to server")
        }

        syncEventsApi.emit(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Окончание выгрузки на сервер",
                duration = duration
            )
        )
    }

    private suspend fun exportFromServer(syncId: String, exportSyncInfo: SyncInformationV2) {
        val syncEntities = syncRepository.getSyncEntities()

        exportSyncInfo.exportPartBufferInformation.exportPartsInformation.forEach { exportPartInformationV2 ->
            val exportPartId = exportPartInformationV2.id
            val entityId = exportPartInformationV2.entityModel.id
            val tableId = exportPartInformationV2.table

            if (syncEntities.contains(entityId to tableId)) {
                val syncEntity = syncEntities.getOrElse(entityId to tableId) {
                    throw IllegalStateException()
                }

                syncEventsApi.emit(
                    SyncEvent.Info(
                        id = UUID.randomUUID().toString(),
                        name = "Старт загрузки entityId $entityId tableId $tableId count ${exportPartInformationV2.count}"
                    )
                )

                val duration = measureTime {
                    try {
                        syncEntity.exportFromServer(syncId, exportPartId)
                    } catch (e: Throwable) {
                        syncEventsApi.emit(
                            SyncEvent.Error(
                                id = UUID.randomUUID().toString(),
                                name = "Ошибка загрузки entityId $entityId tableId $tableId",
                            )
                        )
                    }
                }

                syncEventsApi.emit(
                    SyncEvent.Measured(
                        id = UUID.randomUUID().toString(),
                        name = "Окончание загрузки entityId $entityId tableId $tableId count ${exportPartInformationV2.count}",
                        duration
                    )
                )
            }
        }

        Timber.tag(SYNC_TAG)
            .d("exportPartsInformation count ${exportSyncInfo.exportPartBufferInformation.exportPartsInformation.size}")
    }

    private suspend fun startExportFromServerToLocal(syncId: String) {
        syncEventsApi.emit(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт загрузки с сервера"
            )
        )

        val duration = measureTime {
            val exportSyncInfo = syncControllerApi.apiSyncIdStartExportPost(syncId)
            Timber.tag(SYNC_TAG).d("started export from server to local")

            Timber.tag(SYNC_TAG).d("clear data before download")
            syncRepository.clearDataBeforeDownload()

            exportFromServer(syncId, exportSyncInfo)

            syncControllerApi.apiSyncIdCompleteExportPost(syncId)
            Timber.tag(SYNC_TAG).d("completed export from server to local")
        }

        syncEventsApi.emit(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Окончание загрузки с сервера",
                duration = duration
            )
        )
    }

    companion object {
        const val SYNC_TAG = "SYNC_TAG"
    }
}