package org.openapitools.client.apis

import org.openapitools.client.models.ActionDto
import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.ExtendedActionDto
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

interface ActionCrudControllerApi {
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
    @GET("api/documents/actions")
    suspend fun apiDocumentsActionsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/documents/actions/{id}")
    suspend fun apiDocumentsActionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/documents/actions/{id}")
    suspend fun apiDocumentsActionsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param actionDto  
     * @return [PutResponse]
     */
    @PUT("api/documents/actions/{id}")
    suspend fun apiDocumentsActionsIdPut(@Path("id") id: kotlin.String, @Body actionDto: ActionDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param extendedActionDto  
     * @return [PostResponse]
     */
    @POST("api/documents/actions")
    suspend fun apiDocumentsActionsPost(@Body extendedActionDto: ExtendedActionDto): Response<PostResponse>

}
