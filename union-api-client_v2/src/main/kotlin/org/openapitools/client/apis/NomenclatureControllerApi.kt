package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.NomenclatureDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface NomenclatureControllerApi {
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
    @GET("api/catalogs/nomenclature")
    suspend fun apiCatalogsNomenclatureGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/nomenclature/{id}")
    suspend fun apiCatalogsNomenclatureIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/nomenclature/{id}")
    suspend fun apiCatalogsNomenclatureIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param nomenclatureDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/nomenclature/{id}")
    suspend fun apiCatalogsNomenclatureIdPut(@Path("id") id: kotlin.String, @Body nomenclatureDtoV2: NomenclatureDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param nomenclatureDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/nomenclature")
    suspend fun apiCatalogsNomenclaturePost(@Body nomenclatureDtoV2: NomenclatureDtoV2): Response<PostResponseV2>

}
