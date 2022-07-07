package org.openapitools.client.apis


import org.openapitools.client.models.ChangeRecordsStatusesRequestV2
import org.openapitools.client.models.InventoryResultWithAccountingObjectsDtoV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.SpringResponseV2
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
     * @return [SpringResponseV2]
     */
    @PUT("api/reports/inventory-result-records/{id}/set-checked/single")
    suspend fun apiReportsInventoryResultRecordsIdSetCheckedSinglePut(@Path("id") id: kotlin.String, @Query("newChecked") newChecked: kotlin.Boolean): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param newStatus  
     * @return [SpringResponseV2]
     */
    @PUT("api/reports/inventory-result-records/{id}/set-status/single")
    suspend fun apiReportsInventoryResultRecordsIdSetStatusSinglePut(@Path("id") id: kotlin.String, @Query("newStatus") newStatus: kotlin.String): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param changeRecordsStatusesRequestV2  
     * @return [SpringResponseV2]
     */
    @PUT("api/reports/inventory-results/{id}/changeStatuses")
    suspend fun apiReportsInventoryResultsIdChangeStatusesPut(@Path("id") id: kotlin.String, @Body changeRecordsStatusesRequestV2: ChangeRecordsStatusesRequestV2): Response<SpringResponseV2>

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
     * @return [SpringResponseV2]
     */
    @PUT("api/reports/inventory-results/{id}/set-checked/batch")
    suspend fun apiReportsInventoryResultsIdSetCheckedBatchPut(@Path("id") id: kotlin.String, @Query("filterValues") filterValues: kotlin.String, @Query("newChecked") newChecked: kotlin.Boolean, @Query("searchQuery") searchQuery: kotlin.String? = null): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param newStatus  
     * @return [SpringResponseV2]
     */
    @PUT("api/reports/inventory-results/{id}/set-status/batch")
    suspend fun apiReportsInventoryResultsIdSetStatusBatchPut(@Path("id") id: kotlin.String, @Query("newStatus") newStatus: kotlin.String): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryResultWithAccountingObjectsDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/reports/inventory-results/withAccountingObjects")
    suspend fun apiReportsInventoryResultsWithAccountingObjectsPost(@Body inventoryResultWithAccountingObjectsDtoV2: InventoryResultWithAccountingObjectsDtoV2): Response<PostResponseV2>

}
