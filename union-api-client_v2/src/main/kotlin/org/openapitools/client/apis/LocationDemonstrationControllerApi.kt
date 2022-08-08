package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.FloorInformationDtoV2

interface LocationDemonstrationControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param floorId 
     * @return [FloorInformationDtoV2]
     */
    @GET("api/reports/work-place-schemas/{floorId}/floor-information")
    suspend fun apiReportsWorkPlaceSchemasFloorIdFloorInformationGet(@Path("floorId") floorId: kotlin.String): Response<FloorInformationDtoV2>

}
