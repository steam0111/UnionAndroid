package org.openapitools.client.apis


import org.openapitools.client.models.CounterpartyDtoV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
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

interface CounterpartyControllerApi {
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
    @GET("api/catalogs/counterparties")
    suspend fun apiCatalogsCounterpartiesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param counterpartyDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/counterparties/{id}")
    suspend fun apiCatalogsCounterpartiesIdPut(@Path("id") id: kotlin.String, @Body counterpartyDtoV2: CounterpartyDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param counterpartyDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/counterparties")
    suspend fun apiCatalogsCounterpartiesPost(@Body counterpartyDtoV2: CounterpartyDtoV2): Response<PostResponseV2>

}
