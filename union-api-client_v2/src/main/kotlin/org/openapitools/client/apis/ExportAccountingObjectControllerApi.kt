package org.openapitools.client.apis


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ExportAccountingObjectControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @return [Unit]
     */
    @GET("api/catalogs/accounting-objects/export/exel")
    suspend fun apiCatalogsAccountingObjectsExportExelGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>): Response<Unit>

}
