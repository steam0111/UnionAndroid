package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomDepartmentDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DepartmentApi {
    @GET("api/catalogs/departments")
    suspend fun apiCatalogsDepartmentGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100,
    ): GetAllResponse<CustomDepartmentDto>
}