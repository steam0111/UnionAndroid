package org.openapitools.client.apis

import org.openapitools.client.models.DeleteResponse
import org.openapitools.client.models.GetAllResponse
import org.openapitools.client.models.GetResponse
import org.openapitools.client.models.Pageable
import org.openapitools.client.models.PostResponse
import org.openapitools.client.models.PrintTemplateDto
import org.openapitools.client.models.PutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PrintTemplateCrudControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetAllResponse]
     */
    @GET("api/documents/print-templates")
    suspend fun apiDocumentsPrintTemplatesGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetAllResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [DeleteResponse]
     */
    @DELETE("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [GetResponse]
     */
    @GET("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdGet(@Path("id") id: kotlin.String): Response<GetResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @param printTemplateDto  
     * @return [PutResponse]
     */
    @PUT("api/documents/print-templates/{id}")
    suspend fun apiDocumentsPrintTemplatesIdPut(@Path("id") id: kotlin.String, @Body printTemplateDto: PrintTemplateDto): Response<PutResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param printTemplateDto  
     * @return [PostResponse]
     */
    @POST("api/documents/print-templates")
    suspend fun apiDocumentsPrintTemplatesPost(@Body printTemplateDto: PrintTemplateDto): Response<PostResponse>

}
