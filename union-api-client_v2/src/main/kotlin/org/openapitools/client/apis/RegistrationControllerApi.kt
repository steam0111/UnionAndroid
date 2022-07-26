package org.openapitools.client.apis


import org.openapitools.client.models.RegistrationRequestV2
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
     * @param registrationRequestV2  
     * @return [kotlin.String]
     */
    @POST("api/auth/register")
    suspend fun apiAuthRegisterPost(@Body registrationRequestV2: RegistrationRequestV2): Response<kotlin.String>

}
