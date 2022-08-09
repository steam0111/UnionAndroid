package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2
import org.openapitools.client.models.SpringResponseV2

interface FloorSvgFileControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [SpringResponseV2]
     */
    @DELETE("api/reports/work-place-schemas/{id}/svg-file")
    suspend fun apiReportsWorkPlaceSchemasIdSvgFileDelete(@Path("id") id: kotlin.String): Response<SpringResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [Unit]
     */
    @GET("api/reports/work-place-schemas/{id}/svg-file")
    suspend fun apiReportsWorkPlaceSchemasIdSvgFileGet(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/reports/work-place-schemas/{id}/svg-file")
    suspend fun apiReportsWorkPlaceSchemasIdSvgFilePost(@Path("id") id: kotlin.String, @Body apiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2: ApiReportsWorkPlaceSchemasIdSvgFileDeleteRequestV2? = null): Response<SpringResponseV2>

}
