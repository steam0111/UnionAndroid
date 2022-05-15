package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.DepartmentDto
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

interface DepartmentControllerApi {
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
    @GET("api/catalogs/departments")
    suspend fun apiCatalogsDepartmentsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/departments/{id}")
    suspend fun apiCatalogsDepartmentsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/departments/{id}")
    suspend fun apiCatalogsDepartmentsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param departmentDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/departments/{id}")
    suspend fun apiCatalogsDepartmentsIdPut(@Path("id") id: kotlin.String, @Body departmentDto: DepartmentDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param departmentDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/departments")
    suspend fun apiCatalogsDepartmentsPost(@Body departmentDto: DepartmentDto): Response<PostResponse>

}
