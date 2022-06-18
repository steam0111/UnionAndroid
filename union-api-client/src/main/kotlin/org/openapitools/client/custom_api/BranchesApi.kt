package org.openapitools.client.custom_api

import org.openapitools.client.models.Branch
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BranchesApi {
    @GET("api/catalogs/branches")
    suspend fun apiCatalogsBranchesGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<Branch>
}