package org.openapitools.client.apis


import org.openapitools.client.models.GetMyPermissionsResponseV2
import org.openapitools.client.models.PageableV2
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
     * @return [GetMyPermissionsResponseV2]
     */
    @GET("api/security/permissions/my")
    suspend fun apiSecurityPermissionsMyGet(@Query("requestsParameters") requestsParameters: kotlin.collections.Map<kotlin.String, kotlin.collections.List<kotlin.String>>, @Query("pageable") pageable: PageableV2): Response<GetMyPermissionsResponseV2>

}
