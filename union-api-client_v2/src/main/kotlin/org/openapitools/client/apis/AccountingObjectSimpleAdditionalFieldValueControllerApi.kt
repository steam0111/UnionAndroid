package org.openapitools.client.apis


import org.openapitools.client.models.AccountingObjectSimpleAdditionalFieldValueDtoV2
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

interface AccountingObjectSimpleAdditionalFieldValueControllerApi {
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
    @GET("api/catalogs/accountingObjectSimpleAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param accountingObjectSimpleAdditionalFieldValueDtoV2  
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accountingObjectSimpleAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectSimpleAdditionalFieldValueDtoV2: AccountingObjectSimpleAdditionalFieldValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectSimpleAdditionalFieldValueDtoV2  
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accountingObjectSimpleAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectSimpleAdditionalFieldValuePost(@Body accountingObjectSimpleAdditionalFieldValueDtoV2: AccountingObjectSimpleAdditionalFieldValueDtoV2): Response<PostResponseV2>

}
