package org.openapitools.client.apis


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ExportActionControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [Unit]
     */
    @GET("api/documents/actions/{id}/export/excel")
    suspend fun apiDocumentsActionsIdExportExcelGet(@Path("id") id: kotlin.String): Response<Unit>

}
