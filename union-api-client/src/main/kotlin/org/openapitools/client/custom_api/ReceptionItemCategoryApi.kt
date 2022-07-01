package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomReceptionItemCategoryDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReceptionItemCategoryApi {
    @GET("api/catalogs/orders")
    suspend fun apiCatalogsReceptionItemCategoryGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<CustomReceptionItemCategoryDto?>
}