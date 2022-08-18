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
import org.openapitools.client.models.WorkPlaceSvgElementDtoV2

interface WorkPlaceSvgElementControllerApi {
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
    @GET("api/reports/work-place-schemas/work-place-svg-elements")
    suspend fun apiReportsWorkPlaceSchemasWorkPlaceSvgElementsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/reports/work-place-schemas/work-place-svg-elements/{id}")
    suspend fun apiReportsWorkPlaceSchemasWorkPlaceSvgElementsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/reports/work-place-schemas/work-place-svg-elements/{id}")
    suspend fun apiReportsWorkPlaceSchemasWorkPlaceSvgElementsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param workPlaceSvgElementDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/reports/work-place-schemas/work-place-svg-elements/{id}")
    suspend fun apiReportsWorkPlaceSchemasWorkPlaceSvgElementsIdPut(@Path("id") id: kotlin.String, @Body workPlaceSvgElementDtoV2: WorkPlaceSvgElementDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param workPlaceSvgElementDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/reports/work-place-schemas/work-place-svg-elements")
    suspend fun apiReportsWorkPlaceSchemasWorkPlaceSvgElementsPost(@Body workPlaceSvgElementDtoV2: WorkPlaceSvgElementDtoV2): Response<PostResponseV2>

}
