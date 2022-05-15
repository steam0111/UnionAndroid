package org.openapitools.client.apis

import org.openapitools.client.models.WorkPlaceDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface WorkPlaceSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param workPlaceDto  
     * @return [kotlin.collections.List<WorkPlaceDto>]
     */
    @PUT("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesPut(@Body workPlaceDto: kotlin.collections.List<WorkPlaceDto>): Response<kotlin.collections.List<WorkPlaceDto>>

}
