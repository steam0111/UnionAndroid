package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomLocationDto
import org.openapitools.client.models.CustomLocationsTypeDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("api/catalogs/locations")
    suspend fun apiLocationsGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = Int.MAX_VALUE //TODO пока объектов на бэке не много запрашиваем все
    ): GetAllResponse<CustomLocationDto>

    @GET("api/catalogs/location-types")
    suspend fun apiLocationsTypesGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = Int.MAX_VALUE //TODO пока объектов на бэке не много запрашиваем все
    ): GetAllResponse<CustomLocationsTypeDto>
}