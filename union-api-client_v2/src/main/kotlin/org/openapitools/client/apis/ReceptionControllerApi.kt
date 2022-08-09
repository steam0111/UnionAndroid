package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiDocumentsReceptionsGet200ResponseV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.CreateReceptionRequestV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.ReceptionDtoV2

interface ReceptionControllerApi {
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
    @GET("api/documents/receptions")
    suspend fun apiDocumentsReceptionsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/receptions/{id}")
    suspend fun apiDocumentsReceptionsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/receptions/{id}")
    suspend fun apiDocumentsReceptionsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param receptionDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/receptions/{id}")
    suspend fun apiDocumentsReceptionsIdPut(@Path("id") id: kotlin.String, @Body receptionDtoV2: ReceptionDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createReceptionRequestV2 
     * @return [ApiDocumentsReceptionsGet200ResponseV2]
     */
    @POST("api/documents/receptions")
    suspend fun apiDocumentsReceptionsPost(@Body createReceptionRequestV2: CreateReceptionRequestV2): Response<ApiDocumentsReceptionsGet200ResponseV2>

}
