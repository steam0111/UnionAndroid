package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.EmailRulesDtoV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface EmailRulesCrudControllerApi {
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
    @GET("api/email/email-rules")
    suspend fun apiEmailEmailRulesGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/email/email-rules/{id}")
    suspend fun apiEmailEmailRulesIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/email/email-rules/{id}")
    suspend fun apiEmailEmailRulesIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param emailRulesDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/email/email-rules/{id}")
    suspend fun apiEmailEmailRulesIdPut(@Path("id") id: kotlin.String, @Body emailRulesDtoV2: EmailRulesDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param emailRulesDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/email/email-rules")
    suspend fun apiEmailEmailRulesPost(@Body emailRulesDtoV2: EmailRulesDtoV2): Response<PostResponseV2>

}
