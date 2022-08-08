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
import org.openapitools.client.models.TransitAccountingObjectRecordDtoV2

interface TransitAccountingObjectRecordCrudControllerApi {
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
    @GET("api/documents/transit-accounting-object-records")
    suspend fun apiDocumentsTransitAccountingObjectRecordsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/transit-accounting-object-records/{id}")
    suspend fun apiDocumentsTransitAccountingObjectRecordsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/transit-accounting-object-records/{id}")
    suspend fun apiDocumentsTransitAccountingObjectRecordsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param transitAccountingObjectRecordDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/transit-accounting-object-records/{id}")
    suspend fun apiDocumentsTransitAccountingObjectRecordsIdPut(@Path("id") id: kotlin.String, @Body transitAccountingObjectRecordDtoV2: TransitAccountingObjectRecordDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param transitAccountingObjectRecordDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/transit-accounting-object-records")
    suspend fun apiDocumentsTransitAccountingObjectRecordsPost(@Body transitAccountingObjectRecordDtoV2: TransitAccountingObjectRecordDtoV2): Response<PostResponseV2>

}
