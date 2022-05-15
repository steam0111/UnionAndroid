package org.openapitools.client.apis

import org.openapitools.client.models.InlineObject3
import org.openapitools.client.models.SpringResponse
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
     * @param inlineObject3  (optional)
     * @return [SpringResponse]
     */
    @POST("api/documents/inventories/exel")
    suspend fun apiDocumentsInventoriesExelPost(@Body inlineObject3: InlineObject3? = null): Response<SpringResponse>

}
