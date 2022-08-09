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
import org.openapitools.client.models.VocabularyAdditionalFieldValueDtoV2

interface VocabularyAdditionalFieldValueControllerApi {
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
    @GET("api/catalogs/vocabularyAdditionalFieldValue")
    suspend fun apiCatalogsVocabularyAdditionalFieldValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/vocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/vocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param vocabularyAdditionalFieldValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/vocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsVocabularyAdditionalFieldValueIdPut(@Path("id") id: kotlin.String, @Body vocabularyAdditionalFieldValueDtoV2: VocabularyAdditionalFieldValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param vocabularyAdditionalFieldValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/vocabularyAdditionalFieldValue")
    suspend fun apiCatalogsVocabularyAdditionalFieldValuePost(@Body vocabularyAdditionalFieldValueDtoV2: VocabularyAdditionalFieldValueDtoV2): Response<PostResponseV2>

}
