package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2

interface ActionStatusControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/action-statuses")
    suspend fun apiEnumsActionStatusesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/action-statuses/{id}")
    suspend fun apiEnumsActionStatusesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

}
