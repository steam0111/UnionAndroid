package org.openapitools.client.apis


import org.openapitools.client.models.AccountingObjectDtoV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface DeprecatedAccountingObjectDeprecatedSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectDtoV2  
     * @return [kotlin.collections.List<AccountingObjectDtoV2>]
     */
    @PUT("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsPut(@Body accountingObjectDtoV2: kotlin.collections.List<AccountingObjectDtoV2>): Response<kotlin.collections.List<AccountingObjectDtoV2>>

}
