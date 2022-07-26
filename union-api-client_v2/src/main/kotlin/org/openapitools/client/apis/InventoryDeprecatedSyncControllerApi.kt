package org.openapitools.client.apis


import org.openapitools.client.models.InventoryDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface InventoryDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryDtoV2  
     * @return [kotlin.collections.List<InventoryDtoV2>]
     */
    @PUT("api/documents/inventories")
    suspend fun apiDocumentsInventoriesPut(@Body inventoryDtoV2: kotlin.collections.List<InventoryDtoV2>): Response<kotlin.collections.List<InventoryDtoV2>>

}
