package org.openapitools.client.apis


import org.openapitools.client.models.AddAccountingObjectRequestV2
import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface WriteOffActionControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param addAccountingObjectRequestV2  
     * @return [PutResponseV2]
     */
    @POST("api/documents/write-offs/{id}/add-record")
    suspend fun apiDocumentsWriteOffsIdAddRecordPost(@Path("id") id: kotlin.String, @Body addAccountingObjectRequestV2: AddAccountingObjectRequestV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PutResponseV2]
     */
    @POST("api/documents/write-offs/{id}/complete")
    suspend fun apiDocumentsWriteOffsIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

}
