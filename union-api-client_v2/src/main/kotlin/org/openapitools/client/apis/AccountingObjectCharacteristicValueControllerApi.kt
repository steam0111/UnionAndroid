package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectCharacteristicValueDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectCharacteristicValueControllerApi {
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
    @GET("api/catalogs/accounting-object-characteristic-value")
    suspend fun apiCatalogsAccountingObjectCharacteristicValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accounting-object-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectCharacteristicValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accounting-object-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectCharacteristicValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectCharacteristicValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accounting-object-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectCharacteristicValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectCharacteristicValueDtoV2: AccountingObjectCharacteristicValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectCharacteristicValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accounting-object-characteristic-value")
    suspend fun apiCatalogsAccountingObjectCharacteristicValuePost(@Body accountingObjectCharacteristicValueDtoV2: AccountingObjectCharacteristicValueDtoV2): Response<PostResponseV2>

}
