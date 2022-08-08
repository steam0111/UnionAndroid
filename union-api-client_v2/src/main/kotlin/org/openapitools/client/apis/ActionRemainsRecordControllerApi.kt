package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ActionRemainsRecordDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface ActionRemainsRecordControllerApi {
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
    @GET("api/documents/action-remain-records")
    suspend fun apiDocumentsActionRemainRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/action-remain-records/{id}")
    suspend fun apiDocumentsActionRemainRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/action-remain-records/{id}")
    suspend fun apiDocumentsActionRemainRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param actionRemainsRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/action-remain-records/{id}")
    suspend fun apiDocumentsActionRemainRecordsIdPut(@Path("id") id: kotlin.String, @Body actionRemainsRecordDtoV2: ActionRemainsRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param actionRemainsRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/action-remain-records")
    suspend fun apiDocumentsActionRemainRecordsPost(@Body actionRemainsRecordDtoV2: ActionRemainsRecordDtoV2): Response<PostResponseV2>

}
