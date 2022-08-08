package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody


interface InventoryExportPsbControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Unit]
     */
    @GET("api/documents/inventories/{id}/export-psb/excel")
    suspend fun apiDocumentsInventoriesIdExportPsbExcelGet(@Path("id") id: kotlin.String): Response<Unit>

}
