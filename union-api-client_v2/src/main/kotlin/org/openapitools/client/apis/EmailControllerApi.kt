package org.openapitools.client.apis


import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.SendEmailRequestV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param sendEmailRequestV2  
     * @return [PostResponseV2]
     */
    @POST("api/send-email")
    suspend fun apiSendEmailPost(@Body sendEmailRequestV2: SendEmailRequestV2): Response<PostResponseV2>

}
