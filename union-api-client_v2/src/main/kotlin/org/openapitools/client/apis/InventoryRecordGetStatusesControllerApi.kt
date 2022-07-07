package org.openapitools.client.apis


import org.openapitools.client.models.InventoryRecordStatusesV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryRecordGetStatusesControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [InventoryRecordStatusesV2]
     */
    @GET("api/documents/inventories/{id}/record-statuses")
    suspend fun apiDocumentsInventoriesIdRecordStatusesGet(@Path("id") id: kotlin.String): Response<InventoryRecordStatusesV2>

}
