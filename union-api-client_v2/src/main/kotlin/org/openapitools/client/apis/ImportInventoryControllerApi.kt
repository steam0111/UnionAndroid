package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2
import org.openapitools.client.models.SpringResponseV2

interface ImportInventoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/documents/inventories/exel")
    suspend fun apiDocumentsInventoriesExelPost(@Body apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2: ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2? = null): Response<SpringResponseV2>

}
