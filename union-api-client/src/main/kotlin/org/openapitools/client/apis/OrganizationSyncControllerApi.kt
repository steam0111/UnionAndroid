package org.openapitools.client.apis

import org.openapitools.client.models.OrganizationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface OrganizationSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param organizationDto  
     * @return [kotlin.collections.List<OrganizationDto>]
     */
    @PUT("api/catalogs/organizations")
    suspend fun apiCatalogsOrganizationsPut(@Body organizationDto: kotlin.collections.List<OrganizationDto>): Response<kotlin.collections.List<OrganizationDto>>

}
