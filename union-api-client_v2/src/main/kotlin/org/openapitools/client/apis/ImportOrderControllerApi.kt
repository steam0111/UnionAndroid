package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2

interface ImportOrderControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2  (optional)
     * @return [Unit]
     */
    @POST("api/operations/import-orders/exel")
    suspend fun apiOperationsImportOrdersExelPost(@Body apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2: ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2? = null): Response<Unit>

}
