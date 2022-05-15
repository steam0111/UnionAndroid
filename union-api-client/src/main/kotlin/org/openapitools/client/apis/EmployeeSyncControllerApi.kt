package org.openapitools.client.apis

import org.openapitools.client.models.EmployeeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface EmployeeSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param employeeDto  
     * @return [kotlin.collections.List<EmployeeDto>]
     */
    @PUT("api/catalogs/employees")
    suspend fun apiCatalogsEmployeesPut(@Body employeeDto: kotlin.collections.List<EmployeeDto>): Response<kotlin.collections.List<EmployeeDto>>

}
