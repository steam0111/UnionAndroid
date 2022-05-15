package org.openapitools.client.apis

import org.openapitools.client.models.AccountingObjectDto
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

interface AccountingObjectControllerApi {
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
    @GET("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param accountingObjectDto  
     * @return [PutResponse]
     */
    @PUT("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdPut(@Path("id") id: kotlin.String, @Body accountingObjectDto: AccountingObjectDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectDto  
     * @return [PostResponse]
     */
    @POST("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsPost(@Body accountingObjectDto: AccountingObjectDto): Response<PostResponse>

}
