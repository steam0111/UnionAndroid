package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectSimpleAdditionalFieldValueControllerApi {
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
    @GET("api/catalogs/accountingObjectSimpleAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectSimpleAdditionalFieldValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectSimpleAdditionalFieldValueDtoV2: AccountingObjectSimpleAdditionalFieldValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectSimpleAdditionalFieldValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accountingObjectSimpleAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValuePost(@Body accountingObjectSimpleAdditionalFieldValueDtoV2: AccountingObjectSimpleAdditionalFieldValueDtoV2): Response<PostResponseV2>

}
