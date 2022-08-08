package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.CreateCommissioningRequestItemV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface CommissioningSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param createCommissioningRequestItemV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/commissioning/{id}/add-record")
    suspend fun apiDocumentsCommissioningIdAddRecordPost(@Path("id") id: kotlin.String, @Body createCommissioningRequestItemV2: CreateCommissioningRequestItemV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [PutResponseV2]
     */
    @POST("api/documents/commissioning/{id}/complete")
    suspend fun apiDocumentsCommissioningIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [PostResponseV2]
     */
    @POST("api/documents/commissioning/{id}/generate-print-job")
    suspend fun apiDocumentsCommissioningIdGeneratePrintJobPost(@Path("id") id: kotlin.String): Response<PostResponseV2>

}
