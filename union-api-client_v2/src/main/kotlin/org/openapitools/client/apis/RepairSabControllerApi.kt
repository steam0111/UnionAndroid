package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.SaveRepairRequestV2

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
