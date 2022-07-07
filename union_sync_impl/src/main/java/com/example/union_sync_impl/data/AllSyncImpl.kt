package com.example.union_sync_impl.data

import android.util.Log
import com.example.union_sync_api.data.AllSyncApi
import com.example.union_sync_impl.sync.SyncRepository
import com.itrocket.core.base.CoreDispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.StarSyncRequestV2

class AllSyncImpl(
    private val syncControllerApi: SyncControllerApi,
    private val syncRepository: SyncRepository,
    private val coreDispatchers: CoreDispatchers,
) : AllSyncApi {

    override suspend fun syncAll() = withContext(coreDispatchers.io) {
        startNewSync()
    }

    private suspend fun startNewSync() {
        //TODO - "2021-07-03T10:09:31.603Z" пока берется дефолтная дата
        val syncInfo = syncControllerApi.apiSyncPost(StarSyncRequestV2("2021-07-03T10:09:31.603Z"))
        startExportFromServer(syncInfo.id)
    }

    private suspend fun startExportFromServer(syncId: String) {
        val exportSyncInfo = syncControllerApi.apiSyncIdStartExportPost(syncId)
        val syncEntities = syncRepository.getSyncEntities()

        exportSyncInfo.exportPartBufferInformation.exportPartsInformation.forEach { exportPartInformationV2 ->
            val exportPartId = exportPartInformationV2.id
            val entityId = exportPartInformationV2.entityModel.id

            if (syncEntities.contains(entityId)) {
                val syncEntity = syncEntities.getOrElse(entityId) {
                    throw IllegalStateException()
                }
                try {
                    syncEntity.exportFromServer(syncId, exportPartId)
                } catch (t: Throwable) {
                    Log.e("Error","",t)
                    throw t
                }
            }
        }
    }
}