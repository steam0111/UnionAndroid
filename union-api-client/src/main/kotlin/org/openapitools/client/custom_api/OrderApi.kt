package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomOrderDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderApi {
    @GET("api/catalogs/orders")
    suspend fun apiCatalogsOrderGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<CustomOrderDto?>
}