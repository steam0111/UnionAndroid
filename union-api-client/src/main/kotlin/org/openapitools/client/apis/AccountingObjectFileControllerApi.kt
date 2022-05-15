package org.openapitools.client.apis

import okhttp3.ResponseBody
import org.openapitools.client.infrastructure.CollectionFormats.*
import org.openapitools.client.models.CatalogFilesResponse
import org.openapitools.client.models.InlineObject2
import org.openapitools.client.models.SpringResponse
import retrofit2.Response
import retrofit2.http.*

interface AccountingObjectFileControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @param filename  
     * @return [SpringResponse]
     */
    @DELETE("api/files/accounting-objects/{objectId}/{filename}")
    suspend fun apiFilesAccountingObjectsObjectIdFilenameDelete(@Path("objectId") objectId: kotlin.String, @Path("filename") filename: kotlin.String): Response<SpringResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @param filename  
     * @return [ResponseBody]
     */
    @GET("api/files/accounting-objects/{objectId}/{filename}")
    suspend fun apiFilesAccountingObjectsObjectIdFilenameGet(@Path("objectId") objectId: kotlin.String, @Path("filename") filename: kotlin.String): Response<ResponseBody>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @return [CatalogFilesResponse]
     */
    @GET("api/files/accounting-objects/{objectId}")
    suspend fun apiFilesAccountingObjectsObjectIdGet(@Path("objectId") objectId: kotlin.String): Response<CatalogFilesResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @param inlineObject2  (optional)
     * @return [SpringResponse]
     */
    @POST("api/files/accounting-objects/{objectId}")
    suspend fun apiFilesAccountingObjectsObjectIdPost(@Path("objectId") objectId: kotlin.String, @Body inlineObject2: InlineObject2? = null): Response<SpringResponse>

}
