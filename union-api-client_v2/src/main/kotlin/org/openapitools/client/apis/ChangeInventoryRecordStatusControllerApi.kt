package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.InventoryRecordChangeStatusRequestV2
import org.openapitools.client.models.InventoryRecordChangeStatusResponseV2

interface ChangeInventoryRecordStatusControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryRecordChangeStatusRequestV2 
     * @return [InventoryRecordChangeStatusResponseV2]
     */
    @POST("api/documents/inventory-records/{id}/change-status")
    suspend fun apiDocumentsInventoryRecordsIdChangeStatusPost(@Path("id") id: kotlin.String, @Body inventoryRecordChangeStatusRequestV2: InventoryRecordChangeStatusRequestV2): Response<InventoryRecordChangeStatusResponseV2>

}
