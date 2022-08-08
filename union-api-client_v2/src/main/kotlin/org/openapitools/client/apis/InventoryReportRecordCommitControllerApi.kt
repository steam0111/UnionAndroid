package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.InventoryReportRecordCommitResultV2

interface InventoryReportRecordCommitControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [InventoryReportRecordCommitResultV2]
     */
    @POST("api/reports/inventory-report-records/{id}/commit")
    suspend fun apiReportsInventoryReportRecordsIdCommitPost(@Path("id") id: kotlin.String): Response<InventoryReportRecordCommitResultV2>

}
