package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PremisesDto
import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PremisesControllerApi {
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
    @GET("api/catalogs/premises")
    suspend fun apiCatalogsPremisesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param premisesDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdPut(@Path("id") id: kotlin.String, @Body premisesDto: PremisesDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param premisesDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/premises")
    suspend fun apiCatalogsPremisesPost(@Body premisesDto: PremisesDto): Response<PostResponse>

}
