package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.LinkedAdditionalFieldDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface LinkedAdditionalFieldControllerApi {
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
    @GET("api/catalogs/linkedAdditionalField")
    suspend fun apiCatalogsLinkedAdditionalFieldGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/linkedAdditionalField/{id}")
    suspend fun apiCatalogsLinkedAdditionalFieldIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/linkedAdditionalField/{id}")
    suspend fun apiCatalogsLinkedAdditionalFieldIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param linkedAdditionalFieldDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/linkedAdditionalField/{id}")
    suspend fun apiCatalogsLinkedAdditionalFieldIdPut(@Path("id") id: kotlin.String, @Body linkedAdditionalFieldDtoV2: LinkedAdditionalFieldDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param linkedAdditionalFieldDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/linkedAdditionalField")
    suspend fun apiCatalogsLinkedAdditionalFieldPost(@Body linkedAdditionalFieldDtoV2: LinkedAdditionalFieldDtoV2): Response<PostResponseV2>

}
