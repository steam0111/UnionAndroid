package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.OrderRecordDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface OrderRecordControllerApi {
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
    @GET("api/documents/purchase-order-records")
    suspend fun apiDocumentsPurchaseOrderRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param orderRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/purchase-order-records/{id}")
    suspend fun apiDocumentsPurchaseOrderRecordsIdPut(@Path("id") id: kotlin.String, @Body orderRecordDtoV2: OrderRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param orderRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/purchase-order-records")
    suspend fun apiDocumentsPurchaseOrderRecordsPost(@Body orderRecordDtoV2: OrderRecordDtoV2): Response<PostResponseV2>

}
