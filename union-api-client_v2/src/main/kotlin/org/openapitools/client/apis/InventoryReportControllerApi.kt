package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.InventoryReportDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface InventoryReportControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters 
     * @param pageable 
     * @return [GetAllResponseV2]
     */
    @GET("api/reports/inventory-reports")
    suspend fun apiReportsInventoryReportsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/reports/inventory-reports/{id}")
    suspend fun apiReportsInventoryReportsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/reports/inventory-reports/{id}")
    suspend fun apiReportsInventoryReportsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryReportDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/reports/inventory-reports/{id}")
    suspend fun apiReportsInventoryReportsIdPut(@Path("id") id: kotlin.String, @Body inventoryReportDtoV2: InventoryReportDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryReportDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/reports/inventory-reports")
    suspend fun apiReportsInventoryReportsPost(@Body inventoryReportDtoV2: InventoryReportDtoV2): Response<PostResponseV2>

}
