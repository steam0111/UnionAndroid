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
import org.openapitools.client.models.WriteOffRecordDtoV2

interface WriteOffRecordControllerApi {
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
    @GET("api/documents/write-off-records")
    suspend fun apiDocumentsWriteOffRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/write-off-records/{id}")
    suspend fun apiDocumentsWriteOffRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/write-off-records/{id}")
    suspend fun apiDocumentsWriteOffRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param writeOffRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/write-off-records/{id}")
    suspend fun apiDocumentsWriteOffRecordsIdPut(@Path("id") id: kotlin.String, @Body writeOffRecordDtoV2: WriteOffRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param writeOffRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/write-off-records")
    suspend fun apiDocumentsWriteOffRecordsPost(@Body writeOffRecordDtoV2: WriteOffRecordDtoV2): Response<PostResponseV2>

}
