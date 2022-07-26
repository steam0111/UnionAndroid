package org.openapitools.client.apis


import org.openapitools.client.models.ActionRecordDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface ActionRecordDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionRecordDtoV2  
     * @return [kotlin.collections.List<ActionRecordDtoV2>]
     */
    @PUT("api/documents/action-records")
    suspend fun apiDocumentsActionRecordsPut(@Body actionRecordDtoV2: kotlin.collections.List<ActionRecordDtoV2>): Response<kotlin.collections.List<ActionRecordDtoV2>>

}
