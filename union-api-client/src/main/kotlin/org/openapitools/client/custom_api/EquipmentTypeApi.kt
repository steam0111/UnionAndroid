package org.openapitools.client.custom_api

import org.openapitools.client.models.EquipmentTypeDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EquipmentTypeApi {
    @GET("api/catalogs/equipment-types")
    suspend fun apiCatalogEquipmentTypesGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<EquipmentTypeDto>
}