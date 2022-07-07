package org.openapitools.client.apis


import org.openapitools.client.models.InlineObject4V2
import org.openapitools.client.models.SpringResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImportInventoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inlineObject4V2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/documents/inventories/exel")
    suspend fun apiDocumentsInventoriesExelPost(@Body inlineObject4V2: InlineObject4V2? = null): Response<SpringResponseV2>

}
