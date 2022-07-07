package org.openapitools.client.apis


import org.openapitools.client.models.EmployeeDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface EmployeeDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param employeeDtoV2  
     * @return [kotlin.collections.List<EmployeeDtoV2>]
     */
    @PUT("api/catalogs/employees")
    suspend fun apiCatalogsEmployeesPut(@Body employeeDtoV2: kotlin.collections.List<EmployeeDtoV2>): Response<kotlin.collections.List<EmployeeDtoV2>>

}
