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
import org.openapitools.client.models.ProducerDtoV2
import org.openapitools.client.models.PutResponseV2

interface ProducerControllerApi {
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
    @GET("api/catalogs/producers")
    suspend fun apiCatalogsProducersGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/producers/{id}")
    suspend fun apiCatalogsProducersIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/producers/{id}")
    suspend fun apiCatalogsProducersIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param producerDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/producers/{id}")
    suspend fun apiCatalogsProducersIdPut(@Path("id") id: kotlin.String, @Body producerDtoV2: ProducerDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param producerDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/producers")
    suspend fun apiCatalogsProducersPost(@Body producerDtoV2: ProducerDtoV2): Response<PostResponseV2>

}
