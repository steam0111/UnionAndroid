package org.openapitools.client.custom_auth

import org.openapitools.client.models.AuthJwtRequest
import org.openapitools.client.models.AuthJwtResponse
import org.openapitools.client.models.InvalidateAllJwtRequest
import org.openapitools.client.models.JwtOperationResponse
import org.openapitools.client.models.RefreshJwtRequest
import org.openapitools.client.models.RefreshJwtResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
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
    @POST("api/auth/sign-in")
    suspend fun apiAuthSignInPost(@Body authJwtRequest: AuthJwtRequest? = null): Response<AuthJwtResponse>
}