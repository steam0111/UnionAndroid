package org.openapitools.client.apis


import org.openapitools.client.models.CreateNomenclatureApplicationRequestV2
import org.openapitools.client.models.PostResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface NomenclatureApplicationSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PostResponseV2]
     */
    @POST("api/documents/nomenclature-applications/{id}/cancel")
    suspend fun apiDocumentsNomenclatureApplicationsIdCancelPost(@Path("id") id: kotlin.String): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id  
     * @return [PostResponseV2]
     */
    @POST("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmPost(@Path("id") id: kotlin.String): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param createNomenclatureApplicationRequestV2  
     * @return [PostResponseV2]
     */
    @POST("api/documents/nomenclature-applications")
    suspend fun apiDocumentsNomenclatureApplicationsPost(@Body createNomenclatureApplicationRequestV2: CreateNomenclatureApplicationRequestV2): Response<PostResponseV2>

}
