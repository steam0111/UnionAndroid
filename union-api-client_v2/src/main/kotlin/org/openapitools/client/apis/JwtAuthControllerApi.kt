package org.openapitools.client.apis


import org.openapitools.client.models.AuthJwtRequestV2
import org.openapitools.client.models.AuthJwtResponseV2
import org.openapitools.client.models.InvalidateAllJwtRequestV2
import org.openapitools.client.models.JwtOperationResponseV2
import org.openapitools.client.models.RefreshJwtRequestV2
import org.openapitools.client.models.RefreshJwtResponseV2
import org.openapitools.client.models.VerifyJwtRequestV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.OPTIONS
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface JwtAuthControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @DELETE("api/auth/403")
    suspend fun apiAuth403Delete(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @GET("api/auth/403")
    suspend fun apiAuth403Get(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @HEAD("api/auth/403")
    suspend fun apiAuth403Head(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @OPTIONS("api/auth/403")
    suspend fun apiAuth403Options(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @PATCH("api/auth/403")
    suspend fun apiAuth403Patch(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @POST("api/auth/403")
    suspend fun apiAuth403Post(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @PUT("api/auth/403")
    suspend fun apiAuth403Put(): Response<kotlin.String>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param invalidateAllJwtRequestV2  
     * @return [JwtOperationResponseV2]
     */
    @POST("api/auth/invalidate-all-tokens")
    suspend fun apiAuthInvalidateAllTokensPost(@Body invalidateAllJwtRequestV2: InvalidateAllJwtRequestV2): Response<JwtOperationResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [JwtOperationResponseV2]
     */
    @POST("api/auth/invalidate-token")
    suspend fun apiAuthInvalidateTokenPost(): Response<JwtOperationResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param refreshJwtRequestV2  
     * @return [RefreshJwtResponseV2]
     */
    @POST("api/auth/refresh-token")
    suspend fun apiAuthRefreshTokenPost(@Body refreshJwtRequestV2: RefreshJwtRequestV2): Response<RefreshJwtResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param authJwtRequestV2  (optional)
     * @return [AuthJwtResponseV2]
     */
    @POST("api/auth/sign-in-from-active-directory")
    suspend fun apiAuthSignInFromActiveDirectoryPost(@Body authJwtRequestV2: AuthJwtRequestV2? = null): Response<AuthJwtResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param authJwtRequestV2  (optional)
     * @return [AuthJwtResponseV2]
     */
    @POST("api/auth/sign-in")
    suspend fun apiAuthSignInPost(@Body authJwtRequestV2: AuthJwtRequestV2? = null): Response<AuthJwtResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param verifyJwtRequestV2  
     * @return [JwtOperationResponseV2]
     */
    @POST("api/auth/verify")
    suspend fun apiAuthVerifyPost(@Body verifyJwtRequestV2: VerifyJwtRequestV2): Response<JwtOperationResponseV2>

}
