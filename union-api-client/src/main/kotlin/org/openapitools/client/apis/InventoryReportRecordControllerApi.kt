package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.InventoryReportRecordDto
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryReportRecordControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetAllResponse]
     */
    @GET("api/reports/inventory-report-records")
    suspend fun apiReportsInventoryReportRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param inventoryReportRecordDto  
     * @return [PutResponse]
     */
    @PUT("api/reports/inventory-report-records/{id}")
    suspend fun apiReportsInventoryReportRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryReportRecordDto: InventoryReportRecordDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryReportRecordDto  
     * @return [PostResponse]
     */
    @POST("api/reports/inventory-report-records")
    suspend fun apiReportsInventoryReportRecordsPost(@Body inventoryReportRecordDto: InventoryReportRecordDto): Response<PostResponse>

}
