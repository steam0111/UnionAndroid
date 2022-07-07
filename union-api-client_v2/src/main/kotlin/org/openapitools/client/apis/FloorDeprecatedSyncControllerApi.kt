package org.openapitools.client.apis


import org.openapitools.client.models.FloorDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface FloorDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param floorDtoV2  
     * @return [kotlin.collections.List<FloorDtoV2>]
     */
    @PUT("api/catalogs/floors")
    suspend fun apiCatalogsFloorsPut(@Body floorDtoV2: kotlin.collections.List<FloorDtoV2>): Response<kotlin.collections.List<FloorDtoV2>>

}
