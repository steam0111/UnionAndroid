package org.openapitools.client.apis


import org.openapitools.client.models.WorkPlaceDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface WorkPlaceDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param workPlaceDtoV2  
     * @return [kotlin.collections.List<WorkPlaceDtoV2>]
     */
    @PUT("api/catalogs/work-places")
    suspend fun apiCatalogsWorkPlacesPut(@Body workPlaceDtoV2: kotlin.collections.List<WorkPlaceDtoV2>): Response<kotlin.collections.List<WorkPlaceDtoV2>>

}
