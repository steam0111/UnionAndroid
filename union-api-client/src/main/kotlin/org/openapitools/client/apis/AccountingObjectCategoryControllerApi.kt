package org.openapitools.client.apis

import org.openapitools.client.models.EnumGetResponse
import org.openapitools.client.models.GetAllResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountingObjectCategoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponse]
     */
    @GET("api/enums/accounting-object-categories")
    suspend fun apiEnumsAccountingObjectCategoriesGet(): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponse]
     */
    @GET("api/enums/accounting-object-categories/{id}")
    suspend fun apiEnumsAccountingObjectCategoriesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponse>

}
