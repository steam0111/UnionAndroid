package org.openapitools.client.apis

import org.openapitools.client.models.GroupsActiveDirectoryResponse
import org.openapitools.client.models.UseActiveDirectoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface ActiveDirectoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GroupsActiveDirectoryResponse]
     */
    @GET("api/active-directory/groups")
    suspend fun apiActiveDirectoryGroupsGet(): Response<GroupsActiveDirectoryResponse>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [UseActiveDirectoryResponse]
     */
    @GET("api/active-directory/use")
    suspend fun apiActiveDirectoryUseGet(): Response<UseActiveDirectoryResponse>

}
