package org.openapitools.client.apis

import org.openapitools.client.models.GenerateIdentifyResult
import retrofit2.Response
import retrofit2.http.GET

interface IdentifyControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GenerateIdentifyResult]
     */
    @GET("api/identify-utils/generate")
    suspend fun apiIdentifyUtilsGenerateGet(): Response<GenerateIdentifyResult>

}
