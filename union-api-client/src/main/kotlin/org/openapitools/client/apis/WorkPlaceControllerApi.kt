package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PutResponse
import org.openapitools.client.models.WorkPlaceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkPlaceControllerApi {
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
    @GET("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param workPlaceDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdPut(@Path("id") id: kotlin.String, @Body workPlaceDto: WorkPlaceDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param workPlaceDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesPost(@Body workPlaceDto: WorkPlaceDto): Response<PostResponse>

}
