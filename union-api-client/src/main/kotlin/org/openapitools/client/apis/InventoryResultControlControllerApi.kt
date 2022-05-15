package org.openapitools.client.apis

import org.openapitools.client.models.ChangeRecordsStatusesRequest
import org.openapitools.client.models.InventoryResultWithAccountingObjectsDto
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.SpringResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InventoryResultControlControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param newChecked  
     * @return [SpringResponse]
     */
    @PUT("api/reports/inventory-result-records/{id}/set-checked/single")
    suspend fun apiReportsInventoryResultRecordsIdSetCheckedSinglePut(@Path("id") id: kotlin.String, @Query("newChecked") newChecked: kotlin.Boolean): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param newStatus  
     * @return [SpringResponse]
     */
    @PUT("api/reports/inventory-result-records/{id}/set-status/single")
    suspend fun apiReportsInventoryResultRecordsIdSetStatusSinglePut(@Path("id") id: kotlin.String, @Query("newStatus") newStatus: kotlin.String): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param changeRecordsStatusesRequest  
     * @return [SpringResponse]
     */
    @PUT("api/reports/inventory-results/{id}/changeStatuses")
    suspend fun apiReportsInventoryResultsIdChangeStatusesPut(@Path("id") id: kotlin.String, @Body changeRecordsStatusesRequest: ChangeRecordsStatusesRequest): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param requestsParameters  
     * @return [kotlin.Boolean]
     */
    @GET("api/reports/inventory-results/{id}/is-all-checked")
    suspend fun apiReportsInventoryResultsIdIsAllCheckedGet(@Path("id") id: kotlin.String, @Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>): Response<kotlin.Boolean>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param filterValues  
     * @param newChecked  
     * @param searchQuery  (optional, default to "")
     * @return [SpringResponse]
     */
    @PUT("api/reports/inventory-results/{id}/set-checked/batch")
    suspend fun apiReportsInventoryResultsIdSetCheckedBatchPut(@Path("id") id: kotlin.String, @Query("filterValues") filterValues: kotlin.String, @Query("newChecked") newChecked: kotlin.Boolean, @Query("searchQuery") searchQuery: kotlin.String? = null): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param newStatus  
     * @return [SpringResponse]
     */
    @PUT("api/reports/inventory-results/{id}/set-status/batch")
    suspend fun apiReportsInventoryResultsIdSetStatusBatchPut(@Path("id") id: kotlin.String, @Query("newStatus") newStatus: kotlin.String): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryResultWithAccountingObjectsDto  
     * @return [PostResponse]
     */
    @POST("api/reports/inventory-results/withAccountingObjects")
    suspend fun apiReportsInventoryResultsWithAccountingObjectsPost(@Body inventoryResultWithAccountingObjectsDto: InventoryResultWithAccountingObjectsDto): Response<PostResponse>

}
