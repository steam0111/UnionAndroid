package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectVocabularyAdditionalFieldValueDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectVocabularyAdditionalFieldValueControllerApi {
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
    @GET("api/catalogs/accountingObjectVocabularyAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectVocabularyAdditionalFieldValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accountingObjectVocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyAdditionalFieldValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accountingObjectVocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyAdditionalFieldValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectVocabularyAdditionalFieldValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accountingObjectVocabularyAdditionalFieldValue/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyAdditionalFieldValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectVocabularyAdditionalFieldValueDtoV2: AccountingObjectVocabularyAdditionalFieldValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectVocabularyAdditionalFieldValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accountingObjectVocabularyAdditionalFieldValue")
    suspend fun apiCatalogsAccountingObjectVocabularyAdditionalFieldValuePost(@Body accountingObjectVocabularyAdditionalFieldValueDtoV2: AccountingObjectVocabularyAdditionalFieldValueDtoV2): Response<PostResponseV2>

}
