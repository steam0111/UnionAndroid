package org.openapitools.client.apis

import org.openapitools.client.models.EquipmentTypeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface EquipmentTypeSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param equipmentTypeDto  
     * @return [kotlin.collections.List<EquipmentTypeDto>]
     */
    @PUT("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesPut(@Body equipmentTypeDto: kotlin.collections.List<EquipmentTypeDto>): Response<kotlin.collections.List<EquipmentTypeDto>>

}
