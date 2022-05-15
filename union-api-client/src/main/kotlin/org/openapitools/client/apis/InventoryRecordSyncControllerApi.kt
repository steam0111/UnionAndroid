package org.openapitools.client.apis

import org.openapitools.client.models.InventoryRecordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface InventoryRecordSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryRecordDto  
     * @return [kotlin.collections.List<InventoryRecordDto>]
     */
    @PUT("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsPut(@Body inventoryRecordDto: kotlin.collections.List<InventoryRecordDto>): Response<kotlin.collections.List<InventoryRecordDto>>

}
