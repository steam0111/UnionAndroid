package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.EventFieldDto
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

interface EventFieldCrudControllerApi {
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
    @GET("api/log/event-fields")
    suspend fun apiLogEventFieldsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/log/event-fields/{id}")
    suspend fun apiLogEventFieldsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/log/event-fields/{id}")
    suspend fun apiLogEventFieldsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param eventFieldDto  
     * @return [PutResponse]
     */
    @PUT("api/log/event-fields/{id}")
    suspend fun apiLogEventFieldsIdPut(@Path("id") id: kotlin.String, @Body eventFieldDto: EventFieldDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param eventFieldDto  
     * @return [PostResponse]
     */
    @POST("api/log/event-fields")
    suspend fun apiLogEventFieldsPost(@Body eventFieldDto: EventFieldDto): Response<PostResponse>

}
