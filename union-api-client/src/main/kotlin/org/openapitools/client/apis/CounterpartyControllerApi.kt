package org.openapitools.client.apis

import org.openapitools.client.models.CounterpartyDto
import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CounterpartyControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetAllResponse]
     */
    @GET("api/catalogs/counterparties")
    suspend fun apiCatalogsCounterpartiesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param counterpartyDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdPut(@Path("id") id: kotlin.String, @Body counterpartyDto: CounterpartyDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param counterpartyDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/counterparties")
    suspend fun apiCatalogsCounterpartiesPost(@Body counterpartyDto: CounterpartyDto): Response<PostResponse>

}
