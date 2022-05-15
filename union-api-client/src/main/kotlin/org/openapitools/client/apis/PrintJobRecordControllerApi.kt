package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PrintJobRecordDto
import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PrintJobRecordControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetAllResponse]
     */
    @GET("api/documents/print-job-records")
    suspend fun apiDocumentsPrintJobRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param printJobRecordDto  
     * @return [PutResponse]
     */
    @PUT("api/documents/print-job-records/{id}")
    suspend fun apiDocumentsPrintJobRecordsIdPut(@Path("id") id: kotlin.String, @Body printJobRecordDto: PrintJobRecordDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param printJobRecordDto  
     * @return [PostResponse]
     */
    @POST("api/documents/print-job-records")
    suspend fun apiDocumentsPrintJobRecordsPost(@Body printJobRecordDto: PrintJobRecordDto): Response<PostResponse>

}
