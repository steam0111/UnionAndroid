package org.openapitools.client.apis

import org.openapitools.client.models.ActionRecordDto
import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
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

interface ActionRecordControllerApi {
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
    @GET("api/documents/action-records")
    suspend fun apiDocumentsActionRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/documents/action-records/{id}")
    suspend fun apiDocumentsActionRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/documents/action-records/{id}")
    suspend fun apiDocumentsActionRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param actionRecordDto  
     * @return [PutResponse]
     */
    @PUT("api/documents/action-records/{id}")
    suspend fun apiDocumentsActionRecordsIdPut(@Path("id") id: kotlin.String, @Body actionRecordDto: ActionRecordDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionRecordDto  
     * @return [PostResponse]
     */
    @POST("api/documents/action-records")
    suspend fun apiDocumentsActionRecordsPost(@Body actionRecordDto: ActionRecordDto): Response<PostResponse>

}
