package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.InventoryRecordStatusesV2

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
