package org.openapitools.client.apis


import org.openapitools.client.models.AccountingObjectDtoV2
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

interface AccountingObjectControllerApi {
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
    @GET("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param accountingObjectDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accounting-objects/{id}")
    suspend fun apiCatalogsAccountingObjectsIdPut(@Path("id") id: kotlin.String, @Body accountingObjectDtoV2: AccountingObjectDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsPost(@Body accountingObjectDtoV2: AccountingObjectDtoV2): Response<PostResponseV2>

}
