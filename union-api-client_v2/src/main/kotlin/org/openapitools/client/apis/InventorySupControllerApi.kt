package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PreviewInventoryRequestV2
import org.openapitools.client.models.PreviewInventoryResponseV2
import org.openapitools.client.models.PutResponseV2

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
     * @param structuralUnitId 
     * @param molId 
     * @return [GetResponseV2]
     */
    @GET("api/documents/inventories/locationId/{locationId}/structuralUnitId/{structuralUnitId}/molId/{molId}")
    suspend fun apiDocumentsInventoriesLocationIdLocationIdStructuralUnitIdStructuralUnitIdMolIdMolIdGet(@Path("locationId") locationId: kotlin.String, @Path("structuralUnitId") structuralUnitId: kotlin.String, @Path("molId") molId: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param previewInventoryRequestV2 
     * @return [PreviewInventoryResponseV2]
     */
    @POST("api/documents/inventories/preview")
    suspend fun apiDocumentsInventoriesPreviewPost(@Body previewInventoryRequestV2: PreviewInventoryRequestV2): Response<PreviewInventoryResponseV2>

}
