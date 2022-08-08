package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.PutResponseV2

interface TransitSubControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [PutResponseV2]
     */
    @POST("api/documents/transits/{id}/complete")
    suspend fun apiDocumentsTransitsIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

}
