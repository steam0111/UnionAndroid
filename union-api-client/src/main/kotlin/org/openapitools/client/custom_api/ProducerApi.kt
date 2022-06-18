package org.openapitools.client.custom_api

import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.Producer
import retrofit2.http.GET
import retrofit2.http.Query

interface ProducerApi {
    @GET("api/catalogs/producers")
    suspend fun apiCatalogsProducerGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<Producer>
}