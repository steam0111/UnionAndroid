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
import org.openapitools.client.models.VocabularyAdditionalFieldDtoV2

interface VocabularyAdditionalFieldControllerApi {
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
    @GET("api/catalogs/vocabularyAdditionalField")
    suspend fun apiCatalogsVocabularyAdditionalFieldGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/vocabularyAdditionalField/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/vocabularyAdditionalField/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param vocabularyAdditionalFieldDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/vocabularyAdditionalField/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldIdPut(@Path("id") id: kotlin.String, @Body vocabularyAdditionalFieldDtoV2: VocabularyAdditionalFieldDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param vocabularyAdditionalFieldDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/vocabularyAdditionalField")
    suspend fun apiCatalogsVocabularyAdditionalFieldPost(@Body vocabularyAdditionalFieldDtoV2: VocabularyAdditionalFieldDtoV2): Response<PostResponseV2>

}
