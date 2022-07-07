package org.openapitools.client.apis


import org.openapitools.client.models.FloorInformationDtoV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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
