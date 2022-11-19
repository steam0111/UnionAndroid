package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_api.entity.SyncInfoType
import com.example.union_sync_impl.dao.NetworkSyncDao
import com.example.union_sync_impl.entity.NetworkSyncDb
import com.example.union_sync_impl.sync.SyncEntity
import com.example.union_sync_impl.sync.SyncInfoRepository
import com.example.union_sync_impl.sync.SyncRepository
import com.itrocket.core.base.CoreDispatchers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlinx.coroutines.withContext
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StarSyncRequestV2
import org.openapitools.client.models.SyncInformationV2
import timber.log.Timber

@OptIn(ExperimentalTime::class)
class AllSyncImpl(
    private val syncInfoRepository: SyncInfoRepository,
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

        syncEventsApi.emitSyncEvent(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт синхронизации, время: $dateTime"
            )
        )

        val duration = measureTime {
            val syncInfo = syncControllerApi.apiSyncPost(StarSyncRequestV2(dateTime))
            Timber.tag(SYNC_TAG).d("sync started ${syncInfo.id}")

            val localItemCount = syncInfoRepository.getLocalItemCount()
            if (localItemCount != 0L) {
                syncEventsApi.emitSyncInfoType(
                    SyncInfoType.TitleEvent(
                        title = "Старт выгрузки",
                    )
                )
                startExportFromLocalToServer(
                    syncId = syncInfo.id,
                    localItemCount = localItemCount
                )
            }

            syncEventsApi.emitSyncInfoType(
                SyncInfoType.TitleEvent(
                    title = "Старт загрузки",
                )
            )
            startExportFromServerToLocal(
                syncId = syncInfo.id,
            )

            val syncCompletedInfo = syncControllerApi.apiSyncIdCompleteSyncPost(syncInfo.id)

            Timber.tag(SYNC_TAG).d(
                "sync completed\n" +
                        "isUploadComplete : ${syncCompletedInfo.importComplete}, uploaded parts count: ${syncCompletedInfo.importPartBufferInformation.importRequestsInformation?.size}\n" +
                        "isDownloadComplete : ${syncCompletedInfo.exportComplete}, all parts count from server: ${syncCompletedInfo.exportPartBufferInformation.exportPartsInformation.size}"
            )
            updateLastSyncTime()
        }

        syncEventsApi.emitSyncEvent(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Синхронизация завершена",
                duration = duration
            )
        )
    }

    override suspend fun getLastSyncTime(): Long {
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

    private suspend fun startExportFromLocalToServer(syncId: String, localItemCount: Long) {
        syncEventsApi.emitSyncEvent(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт выгрузки на сервер"
            )
        )
        syncEventsApi.emitSyncInfoType(
            SyncInfoType.ItemCount(
                count = null,
                isAllCount = false
            )
        )
        syncEventsApi.emitSyncInfoType(
            SyncInfoType.ItemCount(
                count = localItemCount,
                isAllCount = true
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

        syncEventsApi.emitSyncEvent(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Окончание выгрузки на сервер",
                duration = duration
            )
        )
    }

    private suspend fun exportFromServer(
        syncId: String,
        exportSyncInfo: SyncInformationV2,
        syncEntities: Map<Pair<String, String>, SyncEntity<*>>
    ) {
        exportSyncInfo.exportPartBufferInformation.exportPartsInformation.forEach { exportPartInformationV2 ->
            val exportPartId = exportPartInformationV2.id
            val entityId = exportPartInformationV2.entityModel.id
            val tableId = exportPartInformationV2.table

            if (syncEntities.contains(entityId to tableId)) {
                val syncEntity = syncEntities.getOrElse(entityId to tableId) {
                    throw IllegalStateException()
                }

                syncEventsApi.emitSyncEvent(
                    SyncEvent.Info(
                        id = UUID.randomUUID().toString(),
                        name = "Старт загрузки entityId $entityId tableId $tableId count ${exportPartInformationV2.count}"
                    )
                )

                val duration = measureTime {
                    try {
                        syncEntity.exportFromServer(syncId, exportPartId)
                    } catch (e: Throwable) {
                        syncEventsApi.emitSyncEvent(
                            SyncEvent.Error(
                                id = UUID.randomUUID().toString(),
                                name = "Ошибка загрузки entityId $entityId tableId $tableId",
                            )
                        )
                    }
                }
                syncEventsApi.emitSyncInfoType(
                    SyncInfoType.ItemCount(
                        count = exportPartInformationV2.count?.toLong() ?: 0L,
                        isAllCount = false,
                    )
                )

                syncEventsApi.emitSyncEvent(
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
        syncEventsApi.emitSyncEvent(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт загрузки с сервера"
            )
        )

        syncEventsApi.emitSyncInfoType(
            SyncInfoType.ItemCount(
                count = null,
                isAllCount = false,
            )
        )
        val duration = measureTime {
            val exportSyncInfo = syncControllerApi.apiSyncIdStartExportPost(syncId)
            val syncEntities = syncRepository.getSyncEntities()

            val serverItemCount = syncInfoRepository.getServerItemCount(
                syncEntities = syncEntities,
                exportSyncInfo = exportSyncInfo
            )

            syncEventsApi.emitSyncInfoType(
                SyncInfoType.ItemCount(
                    count = serverItemCount,
                    isAllCount = true,
                )
            )
            Timber.tag(SYNC_TAG).d("started export from server to local")

            Timber.tag(SYNC_TAG).d("clear data before download")
            syncRepository.clearDataBeforeDownload()

            exportFromServer(syncId, exportSyncInfo, syncEntities)

            syncControllerApi.apiSyncIdCompleteExportPost(syncId)
            Timber.tag(SYNC_TAG).d("completed export from server to local")
        }

        syncEventsApi.emitSyncEvent(
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