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
import org.openapitools.client.models.UnionUserDtoV2

interface UnionUserControllerApi {
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
    @GET("api/security/accounts")
    suspend fun apiSecurityAccountsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/security/accounts/{id}")
    suspend fun apiSecurityAccountsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/security/accounts/{id}")
    suspend fun apiSecurityAccountsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param unionUserDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/security/accounts/{id}")
    suspend fun apiSecurityAccountsIdPut(@Path("id") id: kotlin.String, @Body unionUserDtoV2: UnionUserDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param unionUserDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/security/accounts")
    suspend fun apiSecurityAccountsPost(@Body unionUserDtoV2: UnionUserDtoV2): Response<PostResponseV2>

}
