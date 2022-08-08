package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.CancelNomenclatureApplicationRequestV2
import org.openapitools.client.models.ConfirmNomenclatureApplicationRequestV2
import org.openapitools.client.models.FormExelStatementRequestV2
import org.openapitools.client.models.PostResponseV2

interface NomenclatureApplicationSupControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param formExelStatementRequestV2 
     * @return [Unit]
     */
    @POST("api/documents/export/nomenclature-applications/")
    suspend fun apiDocumentsExportNomenclatureApplicationsPost(@Body formExelStatementRequestV2: FormExelStatementRequestV2): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param cancelNomenclatureApplicationRequest 
     * @return [PostResponseV2]
     */
    @POST("api/documents/nomenclature-applications/{id}/cancel")
    suspend fun apiDocumentsNomenclatureApplicationsIdCancelPost(@Path("id") id: kotlin.String, @Query("cancelNomenclatureApplicationRequest") cancelNomenclatureApplicationRequest: CancelNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @DELETE("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmDelete(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param request 
     * @return [PostResponseV2]
     */
    @GET("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmGet(@Path("id") id: kotlin.String, @Query("request") request: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @HEAD("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmHead(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @OPTIONS("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmOptions(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @PATCH("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmPatch(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @POST("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmPost(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param confirmNomenclatureApplicationRequestV2 
     * @return [PostResponseV2]
     */
    @PUT("api/documents/nomenclature-applications/{id}/confirm")
    suspend fun apiDocumentsNomenclatureApplicationsIdConfirmPut(@Path("id") id: kotlin.String, @Body confirmNomenclatureApplicationRequestV2: ConfirmNomenclatureApplicationRequestV2): Response<PostResponseV2>

}
