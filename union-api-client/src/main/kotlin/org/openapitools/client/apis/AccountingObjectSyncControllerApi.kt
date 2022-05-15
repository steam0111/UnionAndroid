package org.openapitools.client.apis

import org.openapitools.client.models.AccountingObjectDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface AccountingObjectSyncControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param accountingObjectDto  
     * @return [kotlin.collections.List<AccountingObjectDto>]
     */
    @PUT("api/catalogs/accounting-objects")
    suspend fun apiCatalogsAccountingObjectsPut(@Body accountingObjectDto: kotlin.collections.List<AccountingObjectDto>): Response<kotlin.collections.List<AccountingObjectDto>>

}
