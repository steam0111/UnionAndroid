package org.openapitools.client.custom_api

import org.openapitools.client.models.Counterparty
import org.openapitools.client.models.CustomDepartmentDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CounterpartyApi {
    @GET("api/catalogs/counterparties")
    suspend fun apiCatalogsCounterpartyGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<Counterparty>
}