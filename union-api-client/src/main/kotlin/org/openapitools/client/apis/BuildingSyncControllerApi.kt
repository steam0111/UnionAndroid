package org.openapitools.client.apis

import org.openapitools.client.models.BuildingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface BuildingSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param buildingDto  
     * @return [kotlin.collections.List<BuildingDto>]
     */
    @PUT("api/catalogs/buildings")
    suspend fun apiCatalogsBuildingsPut(@Body buildingDto: kotlin.collections.List<BuildingDto>): Response<kotlin.collections.List<BuildingDto>>

}
