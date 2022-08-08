package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.RolePermissionDtoV2

interface RolePermissionControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters 
     * @param pageable 
     * @return [GetAllResponseV2]
     */
    @GET("api/security/role-permissions")
    suspend fun apiSecurityRolePermissionsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param rolePermissionDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/security/role-permissions/{id}")
    suspend fun apiSecurityRolePermissionsIdPut(@Path("id") id: kotlin.String, @Body rolePermissionDtoV2: RolePermissionDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param rolePermissionDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/security/role-permissions")
    suspend fun apiSecurityRolePermissionsPost(@Body rolePermissionDtoV2: RolePermissionDtoV2): Response<PostResponseV2>

}
