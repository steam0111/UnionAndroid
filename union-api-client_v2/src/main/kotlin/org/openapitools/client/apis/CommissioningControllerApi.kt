package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiDocumentsReceptionsGet200ResponseV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.CommissioningDtoV2
import org.openapitools.client.models.CreateCommissioningRequestV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PutResponseV2

interface CommissioningControllerApi {
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
    @GET("api/documents/commissioning")
    suspend fun apiDocumentsCommissioningGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param commissioningDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdPut(@Path("id") id: kotlin.String, @Body commissioningDtoV2: CommissioningDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommissioningRequestV2 
     * @return [ApiDocumentsReceptionsGet200ResponseV2]
     */
    @POST("api/documents/commissioning")
    suspend fun apiDocumentsCommissioningPost(@Body createCommissioningRequestV2: CreateCommissioningRequestV2): Response<ApiDocumentsReceptionsGet200ResponseV2>

}
