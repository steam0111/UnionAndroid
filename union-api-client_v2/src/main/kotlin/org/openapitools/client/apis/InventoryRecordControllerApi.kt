package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.InventoryRecordDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface InventoryRecordControllerApi {
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
    @GET("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/inventory-records/{id}")
    suspend fun apiDocumentsInventoryRecordsIdPut(@Path("id") id: kotlin.String, @Body inventoryRecordDtoV2: InventoryRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/inventory-records")
    suspend fun apiDocumentsInventoryRecordsPost(@Body inventoryRecordDtoV2: InventoryRecordDtoV2): Response<PostResponseV2>

}
