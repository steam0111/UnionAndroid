package org.openapitools.client.apis


import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PreviewInventoryRequestV2
import org.openapitools.client.models.PutResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InventorySupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryId  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/inventories/{inventoryId}/complete")
    suspend fun apiDocumentsInventoriesInventoryIdCompletePut(@Path("inventoryId") inventoryId: kotlin.String): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryId  
     * @param previewInventoryRequestV2  
     * @return [PutResponseV2]
     */
    @PUT("api/documents/inventories/{inventoryId}/in-progress")
    suspend fun apiDocumentsInventoriesInventoryIdInProgressPut(@Path("inventoryId") inventoryId: kotlin.String, @Body previewInventoryRequestV2: PreviewInventoryRequestV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param locationId  
     * @param organisationId  
     * @param molId  
     * @return [GetResponseV2]
     */
    @GET("api/documents/inventories/locationId/{locationId}/organisationId/{organisationId}/molId/{molId}")
    suspend fun apiDocumentsInventoriesLocationIdLocationIdOrganisationIdOrganisationIdMolIdMolIdGet(@Path("locationId") locationId: kotlin.String, @Path("organisationId") organisationId: kotlin.String, @Path("molId") molId: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param previewInventoryRequestV2  
     * @return [GetResponseV2]
     */
    @POST("api/documents/inventories/preview")
    suspend fun apiDocumentsInventoriesPreviewPost(@Body previewInventoryRequestV2: PreviewInventoryRequestV2): Response<GetResponseV2>

}
