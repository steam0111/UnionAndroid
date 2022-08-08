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
import org.openapitools.client.models.ReceptionRecordDtoV2

interface ReceptionRecordControllerApi {
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
    @GET("api/documents/reception-records")
    suspend fun apiDocumentsReceptionRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/reception-records/{id}")
    suspend fun apiDocumentsReceptionRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/reception-records/{id}")
    suspend fun apiDocumentsReceptionRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param receptionRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/reception-records/{id}")
    suspend fun apiDocumentsReceptionRecordsIdPut(@Path("id") id: kotlin.String, @Body receptionRecordDtoV2: ReceptionRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param receptionRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/reception-records")
    suspend fun apiDocumentsReceptionRecordsPost(@Body receptionRecordDtoV2: ReceptionRecordDtoV2): Response<PostResponseV2>

}
