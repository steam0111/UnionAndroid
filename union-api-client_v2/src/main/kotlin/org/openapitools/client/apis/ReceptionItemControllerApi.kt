package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2

interface ReceptionItemControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/reception-item-categories")
    suspend fun apiEnumsReceptionItemCategoriesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/reception-item-categories/{id}")
    suspend fun apiEnumsReceptionItemCategoriesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/remains-categories")
    suspend fun apiEnumsRemainsCategoriesGet(): Response<GetAllResponseV2>

}
