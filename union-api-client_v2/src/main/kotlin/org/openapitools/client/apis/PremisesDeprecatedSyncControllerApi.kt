package org.openapitools.client.apis


import org.openapitools.client.models.PremisesDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface PremisesDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param premisesDtoV2  
     * @return [kotlin.collections.List<PremisesDtoV2>]
     */
    @PUT("api/catalogs/premises")
    suspend fun apiCatalogsPremisesPut(@Body premisesDtoV2: kotlin.collections.List<PremisesDtoV2>): Response<kotlin.collections.List<PremisesDtoV2>>

}
