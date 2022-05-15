package org.openapitools.client.apis

import org.openapitools.client.models.FloorDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface FloorSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param floorDto  
     * @return [kotlin.collections.List<FloorDto>]
     */
    @PUT("api/catalogs/floors")
    suspend fun apiCatalogsFloorsPut(@Body floorDto: kotlin.collections.List<FloorDto>): Response<kotlin.collections.List<FloorDto>>

}
