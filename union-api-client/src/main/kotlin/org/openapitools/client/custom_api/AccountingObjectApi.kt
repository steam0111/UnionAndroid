package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomAccountingObjectDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountingObjectApi {
    @GET("api/catalogs/accounting-objects")
    suspend fun apiAccountingObjectsGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): GetAllResponse<CustomAccountingObjectDto>
}