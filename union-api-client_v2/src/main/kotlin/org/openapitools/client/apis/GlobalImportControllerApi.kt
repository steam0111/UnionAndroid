package org.openapitools.client.apis


import org.openapitools.client.models.ImportProcessedResultV2
import org.openapitools.client.models.InlineObject1V2
import org.openapitools.client.models.InlineObject2V2
import org.openapitools.client.models.SpringResponseV2
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
     * @param inlineObject2V2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/import/exel")
    suspend fun apiImportExelPost(@Body inlineObject2V2: InlineObject2V2? = null): Response<SpringResponseV2>

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
     * @param inlineObject1V2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/import/xml")
    suspend fun apiImportXmlPost(@Body inlineObject1V2: InlineObject1V2? = null): Response<SpringResponseV2>

}
