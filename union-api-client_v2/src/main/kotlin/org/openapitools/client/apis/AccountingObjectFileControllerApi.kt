package org.openapitools.client.apis


import okhttp3.ResponseBody
import org.openapitools.client.models.CatalogFilesResponseV2
import org.openapitools.client.models.InlineObject3V2
import org.openapitools.client.models.SpringResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountingObjectFileControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @param filename  
     * @return [SpringResponseV2]
     */
    @DELETE("api/files/accounting-objects/{objectId}/{filename}")
    suspend fun apiFilesAccountingObjectsObjectIdFilenameDelete(@Path("objectId") objectId: kotlin.String, @Path("filename") filename: kotlin.String): Response<SpringResponseV2>

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
     * @return [CatalogFilesResponseV2]
     */
    @GET("api/files/accounting-objects/{objectId}")
    suspend fun apiFilesAccountingObjectsObjectIdGet(@Path("objectId") objectId: kotlin.String): Response<CatalogFilesResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param objectId  
     * @param inlineObject3V2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/files/accounting-objects/{objectId}")
    suspend fun apiFilesAccountingObjectsObjectIdPost(@Path("objectId") objectId: kotlin.String, @Body inlineObject3V2: InlineObject3V2? = null): Response<SpringResponseV2>

}
