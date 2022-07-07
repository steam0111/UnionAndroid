package org.openapitools.client.apis


import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.SaveRepairRequestV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RepairSabControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PostResponseV2]
     */
    @POST("api/documents/repairs/{id}/complete")
    suspend fun apiDocumentsRepairsIdCompletePost(@Path("id") id: kotlin.String): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param saveRepairRequestV2  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/repairs-update/{id}")
    suspend fun apiDocumentsRepairsUpdateIdPut(@Path("id") id: kotlin.String, @Body saveRepairRequestV2: SaveRepairRequestV2): Response<PutResponseV2>

}
