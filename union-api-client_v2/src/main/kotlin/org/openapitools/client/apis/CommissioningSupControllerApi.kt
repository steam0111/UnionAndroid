package org.openapitools.client.apis


import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface CommissioningSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PutResponseV2]
     */
    @POST("api/documents/commissioning/{id}/complete")
    suspend fun apiDocumentsCommissioningIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PostResponseV2]
     */
    @POST("api/documents/commissioning/{id}/generate-print-job")
    suspend fun apiDocumentsCommissioningIdGeneratePrintJobPost(@Path("id") id: kotlin.String): Response<PostResponseV2>

}
