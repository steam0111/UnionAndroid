package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.InventoryReportRecordDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface InventoryReportRecordControllerApi {
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
    @GET("api/reports/inventory-report-records")
    suspend fun apiReportsInventoryReportRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryReportRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryReportRecordDtoV2: InventoryReportRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryReportRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/reports/inventory-report-records")
    suspend fun apiReportsInventoryReportRecordsPost(@Body inventoryReportRecordDtoV2: InventoryReportRecordDtoV2): Response<PostResponseV2>

}
