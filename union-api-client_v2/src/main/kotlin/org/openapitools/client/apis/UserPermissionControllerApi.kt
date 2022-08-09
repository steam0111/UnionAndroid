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
import org.openapitools.client.models.UserPermissionDtoV2

interface UserPermissionControllerApi {
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
    @GET("api/security/user-permissions")
    suspend fun apiSecurityUserPermissionsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param userPermissionDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/security/user-permissions/{id}")
    suspend fun apiSecurityUserPermissionsIdPut(@Path("id") id: kotlin.String, @Body userPermissionDtoV2: UserPermissionDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userPermissionDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/security/user-permissions")
    suspend fun apiSecurityUserPermissionsPost(@Body userPermissionDtoV2: UserPermissionDtoV2): Response<PostResponseV2>

}
