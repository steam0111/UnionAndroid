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
import org.openapitools.client.models.UserRoleDtoV2

interface UserRoleCrudControllerApi {
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
    @GET("api/security/user-roles")
    suspend fun apiSecurityUserRolesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/security/user-roles/{id}")
    suspend fun apiSecurityUserRolesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/security/user-roles/{id}")
    suspend fun apiSecurityUserRolesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param userRoleDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/security/user-roles/{id}")
    suspend fun apiSecurityUserRolesIdPut(@Path("id") id: kotlin.String, @Body userRoleDtoV2: UserRoleDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userRoleDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/security/user-roles")
    suspend fun apiSecurityUserRolesPost(@Body userRoleDtoV2: UserRoleDtoV2): Response<PostResponseV2>

}
