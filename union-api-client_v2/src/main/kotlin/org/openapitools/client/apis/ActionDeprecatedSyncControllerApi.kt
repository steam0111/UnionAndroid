package org.openapitools.client.apis


import org.openapitools.client.models.ActionDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface ActionDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionDtoV2  
     * @return [kotlin.collections.List<ActionDtoV2>]
     */
    @PUT("api/documents/actions")
    suspend fun apiDocumentsActionsPut(@Body actionDtoV2: kotlin.collections.List<ActionDtoV2>): Response<kotlin.collections.List<ActionDtoV2>>

}
