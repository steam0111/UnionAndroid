package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.SendEmailRequestV2

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
