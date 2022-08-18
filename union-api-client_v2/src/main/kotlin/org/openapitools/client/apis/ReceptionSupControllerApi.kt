package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AddNomenclatureToReceptionRequestV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface ReceptionSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param addNomenclatureToReceptionRequestV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/receptions/{id}/add-nomenclature")
    suspend fun apiDocumentsReceptionsIdAddNomenclaturePost(@Path("id") id: kotlin.String, @Body addNomenclatureToReceptionRequestV2: AddNomenclatureToReceptionRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [PutResponseV2]
     */
    @POST("api/documents/receptions/{id}/complete")
    suspend fun apiDocumentsReceptionsIdCompletePost(@Path("id") id: kotlin.String): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Unit]
     */
    @GET("api/documents/receptions/{id}/export-purchase-invoice")
    suspend fun apiDocumentsReceptionsIdExportPurchaseInvoiceGet(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Unit]
     */
    @GET("api/documents/receptions/{id}/export-reception-units-act")
    suspend fun apiDocumentsReceptionsIdExportReceptionUnitsActGet(@Path("id") id: kotlin.String): Response<Unit>

}
