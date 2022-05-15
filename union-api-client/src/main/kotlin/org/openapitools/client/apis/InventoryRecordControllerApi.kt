package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.InventoryRecordDto
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

interface InventoryRecordControllerApi {
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
    @GET("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param inventoryRecordDto  
     * @return [PutResponse]
     */
    @PUT("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryRecordDto: InventoryRecordDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryRecordDto  
     * @return [PostResponse]
     */
    @POST("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsPost(@Body inventoryRecordDto: InventoryRecordDto): Response<PostResponse>

}
