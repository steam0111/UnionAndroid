package org.openapitools.client.apis


import org.openapitools.client.models.BuildingDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface BuildingDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param buildingDtoV2  
     * @return [kotlin.collections.List<BuildingDtoV2>]
     */
    @PUT("api/catalogs/buildings")
    suspend fun apiCatalogsBuildingsPut(@Body buildingDtoV2: kotlin.collections.List<BuildingDtoV2>): Response<kotlin.collections.List<BuildingDtoV2>>

}
