package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.OrderDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface OrderCrudControllerApi {
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
    @GET("api/documents/purchase-orders")
    suspend fun apiDocumentsPurchaseOrdersGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/purchase-orders/{id}")
    suspend fun apiDocumentsPurchaseOrdersIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/purchase-orders/{id}")
    suspend fun apiDocumentsPurchaseOrdersIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param orderDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/purchase-orders/{id}")
    suspend fun apiDocumentsPurchaseOrdersIdPut(@Path("id") id: kotlin.String, @Body orderDtoV2: OrderDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param orderDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/purchase-orders")
    suspend fun apiDocumentsPurchaseOrdersPost(@Body orderDtoV2: OrderDtoV2): Response<PostResponseV2>

}
