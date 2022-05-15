package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.InventoryResultRecordDto
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

interface InventoryResultRecordCrudControllerApi {
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
    @GET("api/reports/inventory-result-records")
    suspend fun apiReportsInventoryResultRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param inventoryResultRecordDto  
     * @return [PutResponse]
     */
    @PUT("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryResultRecordDto: InventoryResultRecordDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryResultRecordDto  
     * @return [PostResponse]
     */
    @POST("api/reports/inventory-result-records")
    suspend fun apiReportsInventoryResultRecordsPost(@Body inventoryResultRecordDto: InventoryResultRecordDto): Response<PostResponse>

}
