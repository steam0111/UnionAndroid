package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.RegistrationRequestV2

interface RegistrationControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param registrationRequestV2 
     * @return [kotlin.String]
     */
    @POST("api/auth/register")
    suspend fun apiAuthRegisterPost(@Body registrationRequestV2: RegistrationRequestV2): Response<kotlin.String>

}
