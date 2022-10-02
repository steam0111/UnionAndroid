package com.example.union_sync_impl.sync

import com.example.union_sync_api.data.SyncEventsApi
import com.example.union_sync_api.entity.SyncEvent
import com.example.union_sync_impl.data.AllSyncImpl
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ImportPartDtoV2
import timber.log.Timber
import java.lang.reflect.Type
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
@Suppress("BlockingMethodInNonBlockingContext")
abstract class SyncEntity<SyncType>(
    val syncControllerApi: SyncControllerApi,
    val moshi: Moshi
) : KoinComponent {

    private val syncEventApi: SyncEventsApi by inject()

    /**
     * Возможные варианты id, получены через запрос api/security/entity-models
     * actionType, inventoryBase, locationType, remains, employee, inventory, branch,
     * actionStatus, nomenclature, accountingObjectStatus, inventoryState, equipment-type,
     * action, department, entityModelType, accountingCategory, inventoryType, nomenclatureGroup,
     * actionBase, employeeStatus, organization, counterparty, producer, inventoryRecordStatus,
     * location, region, accountingObject, locationPath, transit, transitAccountingObjectRecord, transitRemainsRecord,
     * commissioning, commissioningRecord
     */
    abstract val id: String

    open val table: String = id

    abstract suspend fun exportFromServer(syncId: String, exportPartId: String)

    abstract suspend fun saveInDb(objects: List<SyncType>)

    suspend inline fun <reified T> defaultGetAndSave(syncId: String, exportPartId: String) {
        val objects = getEntitiesFromNetwork<T>(syncId, exportPartId)

        Timber.tag(AllSyncImpl.SYNC_TAG).d("${objects?.size} $id downloaded")

        if (objects != null) {
            saveInDb(objects as List<SyncType>)
        }
    }

    suspend inline fun <reified T> getEntitiesFromNetwork(
        syncId: String,
        exportPartId: String
    ): List<T>? {
        val resultJsonString =
            syncControllerApi.apiSyncSyncIdExportPartsExportPartIdGet(syncId, exportPartId)
        val resultObjectsString = getListEntitiesFromString(resultJsonString)
        return getJsonAdapter<T>().fromJson(resultObjectsString)
    }

    fun getListEntitiesFromString(networkResult: String): String {
        return JSONObject(networkResult).getJSONObject("value").getJSONArray("itemsForExport")
            .toString()
    }

    inline fun <reified T> getJsonAdapter(): JsonAdapter<List<T>> {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter(type)
    }

    suspend fun defaultUpload(syncId: String, dbPartsCollector: Flow<List<Any>>) {
        syncEventApi.emit(
            SyncEvent.Info(
                id = UUID.randomUUID().toString(),
                name = "Старт выгрузки id = $id table = $table"
            )
        )

        val duration = measureTime {
            Timber.tag(AllSyncImpl.SYNC_TAG).d("upload id = $id table = $table")

            dbPartsCollector.collect { objects ->
                Timber.tag(AllSyncImpl.SYNC_TAG).d("${objects.size} $id got from db")

                val importPart = createImportPart(objects)
                syncControllerApi.apiSyncIdImportPartsPost(syncId, importPart)

                Timber.tag(AllSyncImpl.SYNC_TAG).d("${importPart.value?.size} $id uploaded")
            }
            Timber.tag(AllSyncImpl.SYNC_TAG).d("finish id = $id table = $table")
        }

        syncEventApi.emit(
            SyncEvent.Measured(
                id = UUID.randomUUID().toString(),
                name = "Окончание выгрузки $id table = $table",
                duration = duration
            )
        )
    }

    private fun createImportPart(objects: List<Any>) = ImportPartDtoV2(
        id = UUID.randomUUID().toString(),
        value = objects,
        entityModelId = id,
        dateTime = null,
        table = table
    )
}