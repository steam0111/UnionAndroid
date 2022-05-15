package org.openapitools.client.apis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ExportInventoryResultControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [Unit]
     */
    @GET("api/reports/inventory-results/{id}/export/excel")
    suspend fun apiReportsInventoryResultsIdExportExcelGet(@Path("id") id: kotlin.String): Response<Unit>

}
