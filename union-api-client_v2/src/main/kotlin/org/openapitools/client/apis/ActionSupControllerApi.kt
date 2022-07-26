package org.openapitools.client.apis


import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ActionSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PutResponseV2]
     */
    @POST("api/documents/actions/{id}/complete")
    suspend fun apiDocumentsActionsIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

}
