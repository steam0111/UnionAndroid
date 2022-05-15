package org.openapitools.client.apis

import org.openapitools.client.models.GetAllResponse
import retrofit2.Response
import retrofit2.http.GET

interface EntityModelControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponse]
     */
    @GET("api/security/entity-models")
    suspend fun apiSecurityEntityModelsGet(): Response<GetAllResponse>

}
