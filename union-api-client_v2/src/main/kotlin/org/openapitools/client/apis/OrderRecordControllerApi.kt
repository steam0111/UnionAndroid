package org.openapitools.client.apis


import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.OrderRecordDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderRecordControllerApi {
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
    @GET("api/documents/purchase-order-records")
    suspend fun apiDocumentsPurchaseOrderRecordsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param orderRecordDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdPut(@Path("id") id: kotlin.String, @Body orderRecordDtoV2: OrderRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param orderRecordDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/documents/purchase-order-records")
    suspend fun apiDocumentsPurchaseOrderRecordsPost(@Body orderRecordDtoV2: OrderRecordDtoV2): Response<PostResponseV2>

}
