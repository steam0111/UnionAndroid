package org.openapitools.client.apis

import org.openapitools.client.models.GetMyPermissionsResponse
import org.openapitools.client.models.Pageable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExtractMyUserInformationControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters  
     * @param pageable  
     * @return [GetMyPermissionsResponse]
     */
    @GET("api/security/permissions/my")
    suspend fun apiSecurityPermissionsMyGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: Pageable): Response<GetMyPermissionsResponse>

}
