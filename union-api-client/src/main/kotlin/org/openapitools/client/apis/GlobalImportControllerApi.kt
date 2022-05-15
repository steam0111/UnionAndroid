package org.openapitools.client.apis

import org.openapitools.client.models.ImportProcessedResult
import org.openapitools.client.models.InlineObject
import org.openapitools.client.models.InlineObject1
import org.openapitools.client.models.SpringResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GlobalImportControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inlineObject1  (optional)
     * @return [SpringResponse]
     */
    @POST("api/import/exel")
    suspend fun apiImportExelPost(@Body inlineObject1: InlineObject1? = null): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [ImportProcessedResult]
     */
    @GET("api/import/processed")
    suspend fun apiImportProcessedGet(): Response<ImportProcessedResult>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inlineObject  (optional)
     * @return [SpringResponse]
     */
    @POST("api/import/xml")
    suspend fun apiImportXmlPost(@Body inlineObject: InlineObject? = null): Response<SpringResponse>

}
