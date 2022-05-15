package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PutResponse
import org.openapitools.client.models.UserPermissionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserPermissionControllerApi {
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
    @GET("api/security/user-permissions")
    suspend fun apiSecurityUserPermissionsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param userPermissionDto  
     * @return [PutResponse]
     */
    @PUT("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdPut(@Path("id") id: kotlin.String, @Body userPermissionDto: UserPermissionDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userPermissionDto  
     * @return [PostResponse]
     */
    @POST("api/security/user-permissions")
    suspend fun apiSecurityUserPermissionsPost(@Body userPermissionDto: UserPermissionDto): Response<PostResponse>

}
