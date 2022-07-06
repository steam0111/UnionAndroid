package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomRemainsDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReserveApi {
    @GET("api/catalogs/remains")
    suspend fun apiRemainsGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): GetAllResponse<CustomRemainsDto>
}