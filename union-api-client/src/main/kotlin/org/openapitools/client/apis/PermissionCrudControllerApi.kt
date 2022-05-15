package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PermissionDto
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

interface PermissionCrudControllerApi {
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
    @GET("api/security/permissions")
    suspend fun apiSecurityPermissionsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/security/permissions/{id}")
    suspend fun apiSecurityPermissionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/security/permissions/{id}")
    suspend fun apiSecurityPermissionsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param permissionDto  
     * @return [PutResponse]
     */
    @PUT("api/security/permissions/{id}")
    suspend fun apiSecurityPermissionsIdPut(@Path("id") id: kotlin.String, @Body permissionDto: PermissionDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param permissionDto  
     * @return [PostResponse]
     */
    @POST("api/security/permissions")
    suspend fun apiSecurityPermissionsPost(@Body permissionDto: PermissionDto): Response<PostResponse>

}
