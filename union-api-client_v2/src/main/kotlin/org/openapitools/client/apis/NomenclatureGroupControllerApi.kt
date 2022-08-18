package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.NomenclatureGroupDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface NomenclatureGroupControllerApi {
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
    @GET("api/catalogs/nomenclature-group")
    suspend fun apiCatalogsNomenclatureGroupGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/nomenclature-group/{id}")
    suspend fun apiCatalogsNomenclatureGroupIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/nomenclature-group/{id}")
    suspend fun apiCatalogsNomenclatureGroupIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param nomenclatureGroupDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/nomenclature-group/{id}")
    suspend fun apiCatalogsNomenclatureGroupIdPut(@Path("id") id: kotlin.String, @Body nomenclatureGroupDtoV2: NomenclatureGroupDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param nomenclatureGroupDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/nomenclature-group")
    suspend fun apiCatalogsNomenclatureGroupPost(@Body nomenclatureGroupDtoV2: NomenclatureGroupDtoV2): Response<PostResponseV2>

}
