package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.GetMyPermissionsResponseV2
import org.openapitools.client.models.PageableV2

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
    suspend fun apiSecurityPermissionsMyGet(): Response<GetMyPermissionsResponseV2>

}
