package org.openapitools.client.apis


import org.openapitools.client.models.InlineObjectV2
import org.openapitools.client.models.SpringResponseV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
     * @param inlineObjectV2  (optional)
     * @return [SpringResponseV2]
     */
    @POST("api/reports/work-place-schemas/{id}/svg-file")
    suspend fun apiReportsWorkPlaceSchemasIdSvgFilePost(@Path("id") id: kotlin.String, @Body inlineObjectV2: InlineObjectV2? = null): Response<SpringResponseV2>

}
