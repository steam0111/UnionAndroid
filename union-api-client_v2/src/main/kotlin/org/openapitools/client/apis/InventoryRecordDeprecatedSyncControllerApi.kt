package org.openapitools.client.apis


import org.openapitools.client.models.InventoryRecordDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface InventoryRecordDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryRecordDtoV2  
     * @return [kotlin.collections.List<InventoryRecordDtoV2>]
     */
    @PUT("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsPut(@Body inventoryRecordDtoV2: kotlin.collections.List<InventoryRecordDtoV2>): Response<kotlin.collections.List<InventoryRecordDtoV2>>

}
