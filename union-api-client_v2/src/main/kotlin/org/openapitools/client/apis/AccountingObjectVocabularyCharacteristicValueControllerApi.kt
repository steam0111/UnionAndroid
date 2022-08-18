package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.AccountingObjectVocabularyCharacteristicValueDtoV2
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2
import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface AccountingObjectVocabularyCharacteristicValueControllerApi {
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
    @GET("api/catalogs/accounting-object-vocabulary-characteristic-value")
    suspend fun apiCatalogsAccountingObjectVocabularyCharacteristicValueGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/accounting-object-vocabulary-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyCharacteristicValueIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/accounting-object-vocabulary-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyCharacteristicValueIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param accountingObjectVocabularyCharacteristicValueDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/accounting-object-vocabulary-characteristic-value/{id}")
    suspend fun apiCatalogsAccountingObjectVocabularyCharacteristicValueIdPut(@Path("id") id: kotlin.String, @Body accountingObjectVocabularyCharacteristicValueDtoV2: AccountingObjectVocabularyCharacteristicValueDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectVocabularyCharacteristicValueDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/accounting-object-vocabulary-characteristic-value")
    suspend fun apiCatalogsAccountingObjectVocabularyCharacteristicValuePost(@Body accountingObjectVocabularyCharacteristicValueDtoV2: AccountingObjectVocabularyCharacteristicValueDtoV2): Response<PostResponseV2>

}
