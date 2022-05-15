package org.openapitools.client.apis

import org.openapitools.client.models.InventoryRecordChangeStatusRequest
import org.openapitools.client.models.InventoryRecordChangeStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ChangeInventoryRecordStatusControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param inventoryRecordChangeStatusRequest  
     * @return [InventoryRecordChangeStatusResponse]
     */
    @POST("api/documents/inventory-records/{id}/change-status")
    suspend fun apiDocumentsInventoryRecordsIdChangeStatusPost(@Path("id") id: kotlin.String, @Body inventoryRecordChangeStatusRequest: InventoryRecordChangeStatusRequest): Response<InventoryRecordChangeStatusResponse>

}
