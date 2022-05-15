package org.openapitools.client.apis

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ExportInventoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [Unit]
     */
    @GET("api/documents/inventories/{id}/export/excel")
    suspend fun apiDocumentsInventoriesIdExportExcelGet(@Path("id") id: kotlin.String): Response<Unit>

}
