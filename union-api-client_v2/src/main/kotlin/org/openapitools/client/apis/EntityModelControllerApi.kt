package org.openapitools.client.apis


import org.openapitools.client.models.GetAllResponseV2
import retrofit2.Response
import retrofit2.http.GET

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
