package org.openapitools.client.apis


import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.RepairRecordRemainsDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RepairRecordRemainsCrudControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetAllResponseV2]
     */
    @GET("api/documents/repair-records-remains")
    suspend fun apiDocumentsRepairRecordsRemainsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/repair-records-remains/{id}")
    suspend fun apiDocumentsRepairRecordsRemainsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/documents/repair-records-remains/{id}")
    suspend fun apiDocumentsRepairRecordsRemainsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param repairRecordRemainsDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/repair-records-remains/{id}")
    suspend fun apiDocumentsRepairRecordsRemainsIdPut(@Path("id") id: kotlin.String, @Body repairRecordRemainsDtoV2: RepairRecordRemainsDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param repairRecordRemainsDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/documents/repair-records-remains")
    suspend fun apiDocumentsRepairRecordsRemainsPost(@Body repairRecordRemainsDtoV2: RepairRecordRemainsDtoV2): Response<PostResponseV2>

}
