package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PutResponse
import org.openapitools.client.models.RolePermissionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RolePermissionControllerApi {
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
    @GET("api/security/role-permissions")
    suspend fun apiSecurityRolePermissionsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param rolePermissionDto  
     * @return [PutResponse]
     */
    @PUT("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdPut(@Path("id") id: kotlin.String, @Body rolePermissionDto: RolePermissionDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param rolePermissionDto  
     * @return [PostResponse]
     */
    @POST("api/security/role-permissions")
    suspend fun apiSecurityRolePermissionsPost(@Body rolePermissionDto: RolePermissionDto): Response<PostResponse>

}
