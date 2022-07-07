package org.openapitools.client.apis


import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PrintObjectTypeControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/print-object-types")
    suspend fun apiEnumsPrintObjectTypesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/print-object-types/{id}")
    suspend fun apiEnumsPrintObjectTypesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

}
