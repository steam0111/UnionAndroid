package org.openapitools.client.apis

import org.openapitools.client.models.PremisesDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface PremisesSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param premisesDto  
     * @return [kotlin.collections.List<PremisesDto>]
     */
    @PUT("api/catalogs/premises")
    suspend fun apiCatalogsPremisesPut(@Body premisesDto: kotlin.collections.List<PremisesDto>): Response<kotlin.collections.List<PremisesDto>>

}
