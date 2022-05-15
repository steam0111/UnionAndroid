package org.openapitools.client.apis

import org.openapitools.client.models.ActionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface ActionSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionDto  
     * @return [kotlin.collections.List<ActionDto>]
     */
    @PUT("api/documents/actions")
    suspend fun apiDocumentsActionsPut(@Body actionDto: kotlin.collections.List<ActionDto>): Response<kotlin.collections.List<ActionDto>>

}
