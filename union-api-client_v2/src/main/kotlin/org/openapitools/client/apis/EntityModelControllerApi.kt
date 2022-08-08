package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.GetAllResponseV2

interface EntityModelControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/security/entity-models")
    suspend fun apiSecurityEntityModelsGet(): Response<GetAllResponseV2>

}
