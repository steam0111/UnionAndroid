package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.EquipmentTypeDto
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

interface EquipmentTypeControllerApi {
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
    @GET("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param equipmentTypeDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdPut(@Path("id") id: kotlin.String, @Body equipmentTypeDto: EquipmentTypeDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param equipmentTypeDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesPost(@Body equipmentTypeDto: EquipmentTypeDto): Response<PostResponse>

}
