package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2
import org.openapitools.client.models.ImportProcessedResultV2
import org.openapitools.client.models.SpringResponseV2

interface GlobalImportControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/import/exel")
    suspend fun apiImportExelPost(@Body apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2: ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2? = null): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [ImportProcessedResultV2]
     */
    @GET("api/import/processed")
    suspend fun apiImportProcessedGet(): Response<ImportProcessedResultV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/import/xml")
    suspend fun apiImportXmlPost(@Body apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2: ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2? = null): Response<SpringResponseV2>

}
