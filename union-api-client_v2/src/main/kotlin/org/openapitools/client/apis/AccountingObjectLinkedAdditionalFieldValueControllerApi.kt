package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectLinkedAdditionalFieldValueDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectLinkedAdditionalFieldValueControllerApi {
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
    @GET("api/catalogs/accountingObjectLinkedAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectLinkedAdditionalFieldValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accountingObjectLinkedAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectLinkedAdditionalFieldValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accountingObjectLinkedAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectLinkedAdditionalFieldValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectLinkedAdditionalFieldValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accountingObjectLinkedAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectLinkedAdditionalFieldValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectLinkedAdditionalFieldValueDtoV2: AccountingObjectLinkedAdditionalFieldValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectLinkedAdditionalFieldValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accountingObjectLinkedAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectLinkedAdditionalFieldValuePost(@Body accountingObjectLinkedAdditionalFieldValueDtoV2: AccountingObjectLinkedAdditionalFieldValueDtoV2): Response<PostResponseV2>

}
