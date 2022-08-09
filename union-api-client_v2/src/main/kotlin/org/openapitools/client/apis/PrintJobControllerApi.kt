package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.ExtendedPrintJobDtoV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PrintJobDtoV2
import org.openapitools.client.models.PutResponseV2

interface PrintJobControllerApi {
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
    @GET("api/documents/print-jobs")
    suspend fun apiDocumentsPrintJobsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/print-jobs/{id}")
    suspend fun apiDocumentsPrintJobsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/print-jobs/{id}")
    suspend fun apiDocumentsPrintJobsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param printJobDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/print-jobs/{id}")
    suspend fun apiDocumentsPrintJobsIdPut(@Path("id") id: kotlin.String, @Body printJobDtoV2: PrintJobDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param extendedPrintJobDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/print-jobs")
    suspend fun apiDocumentsPrintJobsPost(@Body extendedPrintJobDtoV2: ExtendedPrintJobDtoV2): Response<PostResponseV2>

}
