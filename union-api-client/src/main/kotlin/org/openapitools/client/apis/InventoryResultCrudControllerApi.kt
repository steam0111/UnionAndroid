package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.InventoryResultDto
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

interface InventoryResultCrudControllerApi {
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
    @GET("api/reports/inventory-results")
    suspend fun apiReportsInventoryResultsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/reports/inventory-results/{id}")
    suspend fun apiReportsInventoryResultsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/reports/inventory-results/{id}")
    suspend fun apiReportsInventoryResultsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param inventoryResultDto  
     * @return [PutResponse]
     */
    @PUT("api/reports/inventory-results/{id}")
    suspend fun apiReportsInventoryResultsIdPut(@Path("id") id: kotlin.String, @Body inventoryResultDto: InventoryResultDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryResultDto  
     * @return [PostResponse]
     */
    @POST("api/reports/inventory-results")
    suspend fun apiReportsInventoryResultsPost(@Body inventoryResultDto: InventoryResultDto): Response<PostResponse>

}
