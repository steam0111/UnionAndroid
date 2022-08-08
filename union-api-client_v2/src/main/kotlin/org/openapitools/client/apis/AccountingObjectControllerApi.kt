package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters 
     * @param pageable 
     * @return [GetAllResponseV2]
     */
    @GET("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdPut(@Path("id") id: kotlin.String, @Body accountingObjectDtoV2: AccountingObjectDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsPost(@Body accountingObjectDtoV2: AccountingObjectDtoV2): Response<PostResponseV2>

}
