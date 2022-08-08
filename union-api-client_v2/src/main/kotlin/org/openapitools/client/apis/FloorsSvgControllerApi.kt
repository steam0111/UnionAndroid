package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.FloorsSvgDtoV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface FloorsSvgControllerApi {
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
    @GET("api/catalogs/floorsSvg")
    suspend fun apiCatalogsFloorsSvgGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/floorsSvg/{id}")
    suspend fun apiCatalogsFloorsSvgIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/floorsSvg/{id}")
    suspend fun apiCatalogsFloorsSvgIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param floorsSvgDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/floorsSvg/{id}")
    suspend fun apiCatalogsFloorsSvgIdPut(@Path("id") id: kotlin.String, @Body floorsSvgDtoV2: FloorsSvgDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param floorsSvgDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/floorsSvg")
    suspend fun apiCatalogsFloorsSvgPost(@Body floorsSvgDtoV2: FloorsSvgDtoV2): Response<PostResponseV2>

}
