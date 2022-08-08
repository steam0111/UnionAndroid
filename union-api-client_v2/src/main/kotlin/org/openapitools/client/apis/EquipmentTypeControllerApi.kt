package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.EquipmentTypeDtoV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface EquipmentTypeControllerApi {
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
    @GET("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param equipmentTypeDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/equipment-types/{id}")
    suspend fun apiCatalogsEquipmentTypesIdPut(@Path("id") id: kotlin.String, @Body equipmentTypeDtoV2: EquipmentTypeDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param equipmentTypeDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesPost(@Body equipmentTypeDtoV2: EquipmentTypeDtoV2): Response<PostResponseV2>

}
