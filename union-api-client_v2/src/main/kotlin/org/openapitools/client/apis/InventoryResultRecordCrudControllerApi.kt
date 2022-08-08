package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.InventoryResultRecordDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface InventoryResultRecordCrudControllerApi {
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
    @GET("api/reports/inventory-result-records")
    suspend fun apiReportsInventoryResultRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryResultRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/reports/inventory-result-records/{id}")
    suspend fun apiReportsInventoryResultRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryResultRecordDtoV2: InventoryResultRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryResultRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/reports/inventory-result-records")
    suspend fun apiReportsInventoryResultRecordsPost(@Body inventoryResultRecordDtoV2: InventoryResultRecordDtoV2): Response<PostResponseV2>

}
