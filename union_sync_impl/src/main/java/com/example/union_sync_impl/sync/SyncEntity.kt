package com.example.union_sync_impl.sync

import com.example.union_sync_impl.data.AllSyncImpl
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import org.openapitools.client.custom_api.SyncControllerApi
import org.openapitools.client.models.ImportPartDtoV2
import timber.log.Timber
import java.lang.reflect.Type
import java.util.UUID

@Suppress("BlockingMethodInNonBlockingContext")
abstract class SyncEntity<SyncType>(
    val syncControllerApi: SyncControllerApi,
    val moshi: Moshi
) {
    /**
     * Возможные варианты id, получены через запрос api/security/entity-models
     * ActionType, InventoryBase, locationType, remains, employee, inventory, branch,
     * ActionStatus, nomenclature, AccountingObjectStatus, InventoryState, equipment-type,
     * action, department, EntityModelType, AccountingCategory, InventoryType, nomenclatureGroup,
     * ActionBase, EmployeeStatus, organization, counterparty, producer, InventoryRecordStatus,
     * location, region, accountingObject, locationPath
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

    suspend inline fun <reified T> getEntitiesFromNetwork(syncId: String, exportPartId: String): List<T>? {
        val resultJsonString = syncControllerApi.apiSyncSyncIdExportPartsExportPartIdGet(syncId, exportPartId)
        val resultObjectsString = getListEntitiesFromString(resultJsonString)
        return getJsonAdapter<T>().fromJson(resultObjectsString)
    }

    fun getListEntitiesFromString(networkResult: String): String {
        return JSONObject(networkResult).getJSONObject("value").getJSONArray("itemsForExport").toString()
    }

    inline fun <reified T> getJsonAdapter(): JsonAdapter<List<T>> {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter(type)
    }

    suspend fun defaultUpload(syncId: String, dbPartsCollector: Flow<List<Any>>) {
        Timber.tag(AllSyncImpl.SYNC_TAG).d("upload $id")

        dbPartsCollector.collect { objects ->
            Timber.tag(AllSyncImpl.SYNC_TAG).d("${objects.size} $id got from db")

            val importPart = createImportPart(objects)
            syncControllerApi.apiSyncIdImportPartsPost(syncId, importPart)

            Timber.tag(AllSyncImpl.SYNC_TAG).d("${importPart.value?.size} $id uploaded")
        }
    }

    private fun createImportPart(objects: List<Any>) = ImportPartDtoV2(
        id = UUID.randomUUID().toString(),
        value = objects,
        entityModelId = id,
        dateTime = null
    )
}