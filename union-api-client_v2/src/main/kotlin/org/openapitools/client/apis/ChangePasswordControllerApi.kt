package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ChangePasswordRequestV2

interface ChangePasswordControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param changePasswordRequestV2 
     * @return [kotlin.String]
     */
    @POST("api/auth/change-password")
    suspend fun apiAuthChangePasswordPost(@Body changePasswordRequestV2: ChangePasswordRequestV2): Response<kotlin.String>

}
