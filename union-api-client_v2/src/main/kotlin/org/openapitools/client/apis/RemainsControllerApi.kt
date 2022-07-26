package org.openapitools.client.apis


import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.RemainsDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RemainsControllerApi {
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
    @GET("api/catalogs/remains")
    suspend fun apiCatalogsRemainsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/remains/{id}")
    suspend fun apiCatalogsRemainsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/remains/{id}")
    suspend fun apiCatalogsRemainsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param remainsDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/remains/{id}")
    suspend fun apiCatalogsRemainsIdPut(@Path("id") id: kotlin.String, @Body remainsDtoV2: RemainsDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param remainsDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/remains")
    suspend fun apiCatalogsRemainsPost(@Body remainsDtoV2: RemainsDtoV2): Response<PostResponseV2>

}
