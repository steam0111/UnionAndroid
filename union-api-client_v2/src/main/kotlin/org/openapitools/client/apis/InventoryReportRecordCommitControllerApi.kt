package org.openapitools.client.apis


import org.openapitools.client.models.InventoryReportRecordCommitResultV2
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

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
