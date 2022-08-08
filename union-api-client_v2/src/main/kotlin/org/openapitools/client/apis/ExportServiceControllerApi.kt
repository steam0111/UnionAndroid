package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody


interface ExportServiceControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [kotlin.String]
     */
    @GET("api/documents/export/user-manual")
    suspend fun apiDocumentsExportUserManualGet(): Response<kotlin.String>

}
