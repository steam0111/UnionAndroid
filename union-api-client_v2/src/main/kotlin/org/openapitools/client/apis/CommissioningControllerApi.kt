package org.openapitools.client.apis


import org.openapitools.client.models.CommissioningDtoV2
import org.openapitools.client.models.CreateCommissioningRequestV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.OneOfLessThanPutResponseCommaPostResponseGreaterThanV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommissioningControllerApi {
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
    @GET("api/documents/commissioning")
    suspend fun apiDocumentsCommissioningGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param commissioningDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/commissioning/{id}")
    suspend fun apiDocumentsCommissioningIdPut(@Path("id") id: kotlin.String, @Body commissioningDtoV2: CommissioningDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createCommissioningRequestV2  
     * @return [OneOfLessThanPutResponseCommaPostResponseGreaterThanV2]
     */
    @POST("api/documents/commissioning")
    suspend fun apiDocumentsCommissioningPost(@Body createCommissioningRequestV2: CreateCommissioningRequestV2): Response<OneOfLessThanPutResponseCommaPostResponseGreaterThanV2>

}
