package org.openapitools.client.apis


import org.openapitools.client.models.OrganizationDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface OrganizationDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param organizationDtoV2  
     * @return [kotlin.collections.List<OrganizationDtoV2>]
     */
    @PUT("api/catalogs/organizations")
    suspend fun apiCatalogsOrganizationsPut(@Body organizationDtoV2: kotlin.collections.List<OrganizationDtoV2>): Response<kotlin.collections.List<OrganizationDtoV2>>

}
