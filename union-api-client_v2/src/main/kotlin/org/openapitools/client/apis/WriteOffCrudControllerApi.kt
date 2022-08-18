package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.CreateWriteOffRequestDtoV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.WriteOffDtoV2

interface WriteOffCrudControllerApi {
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
    @GET("api/documents/write-offs")
    suspend fun apiDocumentsWriteOffsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/write-offs/{id}")
    suspend fun apiDocumentsWriteOffsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/write-offs/{id}")
    suspend fun apiDocumentsWriteOffsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param writeOffDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/write-offs/{id}")
    suspend fun apiDocumentsWriteOffsIdPut(@Path("id") id: kotlin.String, @Body writeOffDtoV2: WriteOffDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createWriteOffRequestDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/write-offs")
    suspend fun apiDocumentsWriteOffsPost(@Body createWriteOffRequestDtoV2: CreateWriteOffRequestDtoV2): Response<PostResponseV2>

}
