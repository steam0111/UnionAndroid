package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody


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
    @GET("api/documents/inventories/{id}/export/collation-statement")
    suspend fun apiDocumentsInventoriesIdExportCollationStatementGet(@Path("id") id: kotlin.String): Response<Unit>

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

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Unit]
     */
    @GET("api/documents/inventories/{id}/export/inventory-list")
    suspend fun apiDocumentsInventoriesIdExportInventoryListGet(@Path("id") id: kotlin.String): Response<Unit>

}
