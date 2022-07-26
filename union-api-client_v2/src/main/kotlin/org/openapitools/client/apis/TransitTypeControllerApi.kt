package org.openapitools.client.apis


import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TransitTypeControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/transit-types")
    suspend fun apiEnumsTransitTypesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/transit-types/{id}")
    suspend fun apiEnumsTransitTypesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

}
