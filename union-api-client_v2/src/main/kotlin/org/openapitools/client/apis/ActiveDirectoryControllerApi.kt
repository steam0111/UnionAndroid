package org.openapitools.client.apis


import org.openapitools.client.models.GroupsActiveDirectoryResponseV2
import org.openapitools.client.models.UseActiveDirectoryResponseV2
import retrofit2.Response
import retrofit2.http.GET

interface ActiveDirectoryControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [GroupsActiveDirectoryResponseV2]
     */
    @GET("api/active-directory/groups")
    suspend fun apiActiveDirectoryGroupsGet(): Response<GroupsActiveDirectoryResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [UseActiveDirectoryResponseV2]
     */
    @GET("api/active-directory/use")
    suspend fun apiActiveDirectoryUseGet(): Response<UseActiveDirectoryResponseV2>

}
