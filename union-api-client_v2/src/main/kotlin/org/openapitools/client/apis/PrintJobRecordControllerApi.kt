package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PrintJobRecordDtoV2
import org.openapitools.client.models.PutResponseV2

interface PrintJobRecordControllerApi {
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
    @GET("api/documents/print-job-records")
    suspend fun apiDocumentsPrintJobRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param printJobRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdPut(@Path("id") id: kotlin.String, @Body printJobRecordDtoV2: PrintJobRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param printJobRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/print-job-records")
    suspend fun apiDocumentsPrintJobRecordsPost(@Body printJobRecordDtoV2: PrintJobRecordDtoV2): Response<PostResponseV2>

}
