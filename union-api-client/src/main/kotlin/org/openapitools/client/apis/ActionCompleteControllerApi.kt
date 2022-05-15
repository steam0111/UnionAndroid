package org.openapitools.client.apis

import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ActionCompleteControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PutResponse]
     */
    @POST("api/documents/actions/{id}/complete")
    suspend fun apiDocumentsActionsIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponse>

}
