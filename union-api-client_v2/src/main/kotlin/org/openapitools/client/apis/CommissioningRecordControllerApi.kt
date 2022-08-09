package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.CommissioningRecordDtoV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface CommissioningRecordControllerApi {
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
    @GET("api/documents/commissioning-records")
    suspend fun apiDocumentsCommissioningRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/commissioning-records/{id}")
    suspend fun apiDocumentsCommissioningRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/commissioning-records/{id}")
    suspend fun apiDocumentsCommissioningRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param commissioningRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/commissioning-records/{id}")
    suspend fun apiDocumentsCommissioningRecordsIdPut(@Path("id") id: kotlin.String, @Body commissioningRecordDtoV2: CommissioningRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param commissioningRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/commissioning-records")
    suspend fun apiDocumentsCommissioningRecordsPost(@Body commissioningRecordDtoV2: CommissioningRecordDtoV2): Response<PostResponseV2>

}
