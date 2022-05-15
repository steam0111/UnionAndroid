package org.openapitools.client.apis

import org.openapitools.client.models.InventoryRecordStatuses
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
     * @return [InventoryRecordStatuses]
     */
    @GET("api/documents/inventories/{id}/record-statuses")
    suspend fun apiDocumentsInventoriesIdRecordStatusesGet(@Path("id") id: kotlin.String): Response<InventoryRecordStatuses>

}
