package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PremisesDtoV2
import org.openapitools.client.models.PutResponseV2

interface PremisesControllerApi {
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
    @GET("api/catalogs/premises")
    suspend fun apiCatalogsPremisesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param premisesDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/premises/{id}")
    suspend fun apiCatalogsPremisesIdPut(@Path("id") id: kotlin.String, @Body premisesDtoV2: PremisesDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param premisesDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/premises")
    suspend fun apiCatalogsPremisesPost(@Body premisesDtoV2: PremisesDtoV2): Response<PostResponseV2>

}
