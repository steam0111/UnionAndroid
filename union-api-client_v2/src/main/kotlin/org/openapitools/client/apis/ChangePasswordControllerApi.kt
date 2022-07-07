package org.openapitools.client.apis


import org.openapitools.client.models.ChangePasswordRequestV2
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
     * @param changePasswordRequestV2  
     * @return [kotlin.String]
     */
    @POST("api/auth/change-password")
    suspend fun apiAuthChangePasswordPost(@Body changePasswordRequestV2: ChangePasswordRequestV2): Response<kotlin.String>

}
