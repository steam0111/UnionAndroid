package org.openapitools.client.apis

import org.openapitools.client.models.ActionRecordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface ActionRecordSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionRecordDto  
     * @return [kotlin.collections.List<ActionRecordDto>]
     */
    @PUT("api/documents/action-records")
    suspend fun apiDocumentsActionRecordsPut(@Body actionRecordDto: kotlin.collections.List<ActionRecordDto>): Response<kotlin.collections.List<ActionRecordDto>>

}
