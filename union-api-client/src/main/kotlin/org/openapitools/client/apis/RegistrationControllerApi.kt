package org.openapitools.client.apis

import org.openapitools.client.models.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param registrationRequest  
     * @return [kotlin.String]
     */
    @POST("api/auth/register")
    suspend fun apiAuthRegisterPost(@Body registrationRequest: RegistrationRequest): Response<kotlin.String>

}
