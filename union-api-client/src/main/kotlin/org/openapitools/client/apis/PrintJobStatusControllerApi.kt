package org.openapitools.client.apis

import org.openapitools.client.models.EnumGetResponse
import org.openapitools.client.models.GetAllResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PrintJobStatusControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponse]
     */
    @GET("api/enums/print-job-statuses")
    suspend fun apiEnumsPrintJobStatusesGet(): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [EnumGetResponse]
     */
    @GET("api/enums/print-job-statuses/{id}")
    suspend fun apiEnumsPrintJobStatusesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponse>

}
