package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomOrganizationDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrganizationApi {
    @GET("api/catalogs/organizations")
    suspend fun apiCatalogsOrganizationGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<CustomOrganizationDto>
}