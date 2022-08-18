package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.GenerateIdentifyResultV2

interface IdentifyControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GenerateIdentifyResultV2]
     */
    @GET("api/identify-utils/generate")
    suspend fun apiIdentifyUtilsGenerateGet(): Response<GenerateIdentifyResultV2>

}
