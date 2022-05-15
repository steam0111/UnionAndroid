package org.openapitools.client.apis

import org.openapitools.client.models.InventoryReportRecordCommitResult
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
     * @return [InventoryReportRecordCommitResult]
     */
    @POST("{id}/commit")
    suspend fun idCommitPost(@Path("id") id: kotlin.String): Response<InventoryReportRecordCommitResult>

}
