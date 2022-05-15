package org.openapitools.client.apis

import org.openapitools.client.models.ChangePasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangePasswordControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param changePasswordRequest  
     * @return [kotlin.String]
     */
    @POST("api/auth/change-password")
    suspend fun apiAuthChangePasswordPost(@Body changePasswordRequest: ChangePasswordRequest): Response<kotlin.String>

}
