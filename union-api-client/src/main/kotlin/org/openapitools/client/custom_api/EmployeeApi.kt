package org.openapitools.client.custom_api

import org.openapitools.client.models.CustomEmployeeDto
import org.openapitools.client.models.GetAllResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeApi {
    @GET("api/catalogs/employees")
    suspend fun apiCatalogsEmployeesGet(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): GetAllResponse<CustomEmployeeDto>
}