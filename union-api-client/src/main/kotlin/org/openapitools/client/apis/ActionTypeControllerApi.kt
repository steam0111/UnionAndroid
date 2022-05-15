package org.openapitools.client.apis

import org.openapitools.client.models.EnumGetResponse
import org.openapitools.client.models.GetAllResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActionTypeControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponse]
     */
    @GET("api/enums/action-types")
    suspend fun apiEnumsActionTypesGet(): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponse]
     */
    @GET("api/enums/action-types/{id}")
    suspend fun apiEnumsActionTypesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponse>

}
