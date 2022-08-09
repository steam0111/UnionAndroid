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
import org.openapitools.client.models.PutResponseV2
import org.openapitools.client.models.WorkPlaceDtoV2

interface WorkPlaceControllerApi {
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
    @GET("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param workPlaceDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/work-places/{id}")
    suspend fun apiCatalogsWorkPlacesIdPut(@Path("id") id: kotlin.String, @Body workPlaceDtoV2: WorkPlaceDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param workPlaceDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesPost(@Body workPlaceDtoV2: WorkPlaceDtoV2): Response<PostResponseV2>

}
