package org.openapitools.client.custom_auth

import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.NomenclatureGroupDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NomenclatureGroupApi {
    @GET("api/catalogs/nomenclature-group")
    suspend fun apiCatalogsNomenclatureGroupGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): Response<GetAllResponse<NomenclatureGroupDto>>
}