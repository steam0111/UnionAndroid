package org.openapitools.client.apis

import org.openapitools.client.models.EnumGetResponse
import org.openapitools.client.models.GetAllResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryTypeControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponse]
     */
    @GET("api/enums/inventory-types")
    suspend fun apiEnumsInventoryTypesGet(): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponse]
     */
    @GET("api/enums/inventory-types/{id}")
    suspend fun apiEnumsInventoryTypesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponse>

}
