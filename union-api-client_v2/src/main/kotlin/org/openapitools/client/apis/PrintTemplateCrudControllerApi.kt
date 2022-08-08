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
import org.openapitools.client.models.PrintTemplateDtoV2
import org.openapitools.client.models.PutResponseV2

interface PrintTemplateCrudControllerApi {
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
    @GET("api/documents/print-templates")
    suspend fun apiDocumentsPrintTemplatesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param printTemplateDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdPut(@Path("id") id: kotlin.String, @Body printTemplateDtoV2: PrintTemplateDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param printTemplateDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/print-templates")
    suspend fun apiDocumentsPrintTemplatesPost(@Body printTemplateDtoV2: PrintTemplateDtoV2): Response<PostResponseV2>

}
