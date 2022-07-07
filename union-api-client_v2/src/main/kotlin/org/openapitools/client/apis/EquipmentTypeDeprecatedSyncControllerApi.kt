package org.openapitools.client.apis


import org.openapitools.client.models.EquipmentTypeDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface EquipmentTypeDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param equipmentTypeDtoV2  
     * @return [kotlin.collections.List<EquipmentTypeDtoV2>]
     */
    @PUT("api/catalogs/equipment-types")
    suspend fun apiCatalogsEquipmentTypesPut(@Body equipmentTypeDtoV2: kotlin.collections.List<EquipmentTypeDtoV2>): Response<kotlin.collections.List<EquipmentTypeDtoV2>>

}
