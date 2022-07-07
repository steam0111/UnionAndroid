package org.openapitools.client.apis


import org.openapitools.client.models.GenerateIdentifyResultV2
import retrofit2.Response
import retrofit2.http.GET

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
