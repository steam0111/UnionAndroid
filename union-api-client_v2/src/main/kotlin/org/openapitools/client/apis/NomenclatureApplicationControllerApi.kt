package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.CreateNomenclatureApplicationRequestV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.NomenclatureApplicationDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface NomenclatureApplicationControllerApi {
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
    @GET("api/documents/nomenclature-applications")
    suspend fun apiDocumentsNomenclatureApplicationsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/documents/nomenclature-applications/{id}")
    suspend fun apiDocumentsNomenclatureApplicationsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/documents/nomenclature-applications/{id}")
    suspend fun apiDocumentsNomenclatureApplicationsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param nomenclatureApplicationDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/documents/nomenclature-applications/{id}")
    suspend fun apiDocumentsNomenclatureApplicationsIdPut(@Path("id") id: kotlin.String, @Body nomenclatureApplicationDtoV2: NomenclatureApplicationDtoV2): Response<PutResponseV2>

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
