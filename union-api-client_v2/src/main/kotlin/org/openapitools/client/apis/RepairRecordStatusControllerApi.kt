package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.EnumGetResponseV2
import org.openapitools.client.models.GetAllResponseV2

interface RepairRecordStatusControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GetAllResponseV2]
     */
    @GET("api/enums/repair-record-statuses")
    suspend fun apiEnumsRepairRecordStatusesGet(): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [EnumGetResponseV2]
     */
    @GET("api/enums/repair-record-statuses/{id}")
    suspend fun apiEnumsRepairRecordStatusesIdGet(@Path("id") id: kotlin.String): Response<EnumGetResponseV2>

}
