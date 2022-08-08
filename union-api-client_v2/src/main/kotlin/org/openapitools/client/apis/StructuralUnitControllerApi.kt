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
import org.openapitools.client.models.StructuralUnitDtoV2

interface StructuralUnitControllerApi {
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
    @GET("api/catalogs/structural-units")
    suspend fun apiCatalogsStructuralUnitsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/structural-units/{id}")
    suspend fun apiCatalogsStructuralUnitsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/structural-units/{id}")
    suspend fun apiCatalogsStructuralUnitsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param structuralUnitDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/structural-units/{id}")
    suspend fun apiCatalogsStructuralUnitsIdPut(@Path("id") id: kotlin.String, @Body structuralUnitDtoV2: StructuralUnitDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param structuralUnitDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/structural-units")
    suspend fun apiCatalogsStructuralUnitsPost(@Body structuralUnitDtoV2: StructuralUnitDtoV2): Response<PostResponseV2>

}
