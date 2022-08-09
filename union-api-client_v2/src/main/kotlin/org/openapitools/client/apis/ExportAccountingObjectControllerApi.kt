package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2


interface ExportAccountingObjectControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters 
     * @return [Unit]
     */
    @GET("api/catalogs/accounting-objects/export/exel")
    suspend fun apiCatalogsAccountingObjectsExportExelGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2): Response<Unit>

}
