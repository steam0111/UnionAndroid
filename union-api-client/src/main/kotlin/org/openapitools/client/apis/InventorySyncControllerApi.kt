package org.openapitools.client.apis

import org.openapitools.client.models.InventoryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface InventorySyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryDto  
     * @return [kotlin.collections.List<InventoryDto>]
     */
    @PUT("api/documents/inventories")
    suspend fun apiDocumentsInventoriesPut(@Body inventoryDto: kotlin.collections.List<InventoryDto>): Response<kotlin.collections.List<InventoryDto>>

}
