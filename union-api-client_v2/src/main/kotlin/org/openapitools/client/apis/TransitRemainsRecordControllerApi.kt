package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.TransitRemainsRecordDtoV2

interface TransitRemainsRecordControllerApi {
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
    @GET("api/documents/transit-remains-records")
    suspend fun apiDocumentsTransitRemainsRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/transit-remains-records/{id}")
    suspend fun apiDocumentsTransitRemainsRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/transit-remains-records/{id}")
    suspend fun apiDocumentsTransitRemainsRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param transitRemainsRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/transit-remains-records/{id}")
    suspend fun apiDocumentsTransitRemainsRecordsIdPut(@Path("id") id: kotlin.String, @Body transitRemainsRecordDtoV2: TransitRemainsRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param transitRemainsRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/transit-remains-records")
    suspend fun apiDocumentsTransitRemainsRecordsPost(@Body transitRemainsRecordDtoV2: TransitRemainsRecordDtoV2): Response<PostResponseV2>

}
