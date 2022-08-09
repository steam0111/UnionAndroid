package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.GenerateInventoryNumberRequestV2
import org.openapitools.client.models.GenerateNewInventoryNumberResponseV2

interface AccountingObjectInventoryNumberControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param generateInventoryNumberRequestV2 
     * @return [GenerateNewInventoryNumberResponseV2]
     */
    @POST("api/catalogs/accounting-objects/{id}/generate-inventory-number")
    suspend fun apiCatalogsAccountingObjectsIdGenerateInventoryNumberPost(@Path("id") id: kotlin.String, @Body generateInventoryNumberRequestV2: GenerateInventoryNumberRequestV2): Response<GenerateNewInventoryNumberResponseV2>

}
