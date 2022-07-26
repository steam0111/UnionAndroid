package org.openapitools.client.apis


import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryBaseControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/inventory-bases")
    suspend fun apiEnumsInventoryBasesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/inventory-bases/{id}")
    suspend fun apiEnumsInventoryBasesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

}
