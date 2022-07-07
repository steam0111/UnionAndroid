package com.example.union_sync_impl.sync

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONObject
import org.openapitools.client.custom_api.SyncControllerApi
import java.lang.reflect.Type

@Suppress("BlockingMethodInNonBlockingContext")
abstract class SyncEntity<SyncType>(
    val syncControllerApi: SyncControllerApi,
    val moshi: Moshi
) {
    /**
     * Возможные варианты id, получены через запрос api/security/entity-models
     * [
    "emailRules",
    "NomenclatureApplication",
    "NomenclatureApplicationHistory",
    "accountingObjectLinkedAdditionalFieldValue",
    "accountingObjectSimpleAdditionalFieldValue",
    "accountingObjectVocabularyAdditionalFieldValue",
    "linkedAdditionalField",
    "simpleAdditionalField",
    "vocabularyAdditionalField",
    "vocabularyAdditionalFieldValue",
    "commissioning",
    "action",
    "event",
    "printJob",
    "order",
    "building",
    "inventory",
    "counterparty",
    "nomenclature",
    "nomenclatureGroup",
    "onwed",
    "accountingObject",
    "organization",
    "remains",
    "department",
    "emailSendEvent",
    "inventoryReport",
    "unionUser",
    "premises",
    "reception",
    "producer",
    "workplace",
    "permission",
    "region",
    "repair",
    "unionRole",
    "inventoryResult",
    "employee",
    "writeOff",
    "equipmentType",
    "transit",
    "branch",
    "characteristic",
    "accountingObjectCharacteristicValue",
    "inventoryNumberTemplate",
    "printTemplate",
    "floor",
    "location",
    "locationType"
    ]
     */
    abstract val id: String

    abstract suspend fun exportFromServer(syncId: String, exportPartId: String)

    abstract suspend fun saveInDb(objects: List<SyncType>)

    suspend inline fun <reified T> defaultGetAndSave(syncId: String, exportPartId: String) {
        val objects = getEntitiesFromNetwork<T>(syncId, exportPartId)

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
}