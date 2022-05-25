package org.openapitools.client.apis

import org.openapitools.client.models.AuthJwtRequest
import org.openapitools.client.models.AuthJwtResponse
import org.openapitools.client.models.InvalidateAllJwtRequest
import org.openapitools.client.models.JwtOperationResponse
import org.openapitools.client.models.RefreshJwtRequest
import org.openapitools.client.models.RefreshJwtResponse
import org.openapitools.client.models.VerifyJwtRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
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
     * @param invalidateAllJwtRequest
     * @return [JwtOperationResponse]
     */
    @POST("api/auth/invalidate-all-tokens")
    suspend fun apiAuthInvalidateAllTokensPost(@Body invalidateAllJwtRequest: InvalidateAllJwtRequest): Response<JwtOperationResponse>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @return [JwtOperationResponse]
     */
    @POST("api/auth/invalidate-token")
    suspend fun apiAuthInvalidateTokenPost(@Header("Authorization") bearerToken: String): Response<JwtOperationResponse>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param refreshJwtRequest
     * @return [RefreshJwtResponse]
     */
    @POST("api/auth/refresh-token")
    suspend fun apiAuthRefreshTokenPost(
        @Body refreshJwtRequest: RefreshJwtRequest,
        @Header("Authorization") bearerToken: String
    ): Response<RefreshJwtResponse>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param authJwtRequest  (optional)
     * @return [AuthJwtResponse]
     */
    @POST("api/auth/sign-in-from-active-directory")
    suspend fun apiAuthSignInFromActiveDirectoryPost(@Body authJwtRequest: AuthJwtRequest? = null): Response<AuthJwtResponse>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param authJwtRequest  (optional)
     * @return [AuthJwtResponse]
     */
    @POST("api/auth/sign-in")
    suspend fun apiAuthSignInPost(@Body authJwtRequest: AuthJwtRequest? = null): Response<AuthJwtResponse>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param verifyJwtRequest
     * @return [JwtOperationResponse]
     */
    @POST("api/auth/verify")
    suspend fun apiAuthVerifyPost(@Body verifyJwtRequest: VerifyJwtRequest): Response<JwtOperationResponse>

}
