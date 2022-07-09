package org.openapitools.client.apis


import org.openapitools.client.models.ExportPartBufferInformationV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.ImportPartDtoV2
import org.openapitools.client.models.StarSyncRequestV2
import org.openapitools.client.models.SyncInformationV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SyncControllerApi {
    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @return [SyncInformationV2]
     */
    @POST("api/sync/{id}/complete-export")
    suspend fun apiSyncIdCompleteExportPost(@Path("id") id: kotlin.String): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @return [ExportPartBufferInformationV2]
     */
    @POST("api/sync/{id}/complete-import")
    suspend fun apiSyncIdCompleteImportPost(@Path("id") id: kotlin.String): Response<ExportPartBufferInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @return [SyncInformationV2]
     */
    @POST("api/sync/{id}/complete-sync")
    suspend fun apiSyncIdCompleteSyncPost(@Path("id") id: kotlin.String): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @param importPartDtoV2
     * @return [SyncInformationV2]
     */
    @POST("api/sync/{id}/import-parts/")
    suspend fun apiSyncIdImportPartsPost(@Path("id") id: kotlin.String, @Body importPartDtoV2: ImportPartDtoV2): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @return [SyncInformationV2]
     */
    @POST("api/sync/{id}/start-export")
    suspend fun apiSyncIdStartExportPost(@Path("id") id: kotlin.String): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param id
     * @return [SyncInformationV2]
     */
    @POST("api/sync/{id}/start-import")
    suspend fun apiSyncIdStartImportPost(@Path("id") id: kotlin.String): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param starSyncRequestV2
     * @return [SyncInformationV2]
     */
    @POST("api/sync")
    suspend fun apiSyncPost(@Body starSyncRequestV2: StarSyncRequestV2): Response<SyncInformationV2>

    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param syncId
     * @param exportPartId
     * @return [GetResponseV2]
     */
    @GET("api/sync/{syncId}/export-parts/exportPartId}")
    suspend fun apiSyncSyncIdExportPartsExportPartIdGet(
        @Path("syncId") syncId: kotlin.String,
        @Path("exportPartId") exportPartId: kotlin.String
    ): Response<GetResponseV2>

}
