package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.CharacteristicDtoV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface CharacteristicControllerApi {
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
    @GET("api/catalogs/characteristics")
    suspend fun apiCatalogsCharacteristicsGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/characteristics/{id}")
    suspend fun apiCatalogsCharacteristicsIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/characteristics/{id}")
    suspend fun apiCatalogsCharacteristicsIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param characteristicDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/characteristics/{id}")
    suspend fun apiCatalogsCharacteristicsIdPut(@Path("id") id: kotlin.String, @Body characteristicDtoV2: CharacteristicDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param characteristicDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/characteristics")
    suspend fun apiCatalogsCharacteristicsPost(@Body characteristicDtoV2: CharacteristicDtoV2): Response<PostResponseV2>

}
