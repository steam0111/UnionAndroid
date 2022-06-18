package org.openapitools.client.custom_api

import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.Region
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionApi {
    @GET("api/catalogs/regions")
    suspend fun apiCatalogRegionGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<Region?>
}