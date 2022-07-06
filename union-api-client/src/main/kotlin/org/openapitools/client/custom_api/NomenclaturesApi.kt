package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomNomenclatureDto
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.NomenclatureGroupDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NomenclaturesApi {
    @GET("api/catalogs/nomenclature-group")
    suspend fun apiCatalogsNomenclatureGroupGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<NomenclatureGroupDto>

    @GET("/api/catalogs/nomenclature")
    suspend fun apiCatalogsNomenclatureGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<CustomNomenclatureDto>
}