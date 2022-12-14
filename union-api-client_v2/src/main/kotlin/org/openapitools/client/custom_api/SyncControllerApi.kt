package org.openapitools.client.custom_api

import okhttp3.MultipartBody
import org.openapitools.client.models.ExportPartBufferInformationV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.ImportPartDtoV2
import org.openapitools.client.models.StarSyncRequestV2
import org.openapitools.client.models.SyncInformationV2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun apiSyncIdCompleteExportPost(@Path("id") id: String): Response<SyncInformationV2>

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
    suspend fun apiSyncIdCompleteImportPost(@Path("id") id: String): Response<ExportPartBufferInformationV2>

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
    suspend fun apiSyncIdCompleteSyncPost(@Path("id") id: String): SyncInformationV2

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
    @JvmSuppressWildcards
    suspend fun apiSyncIdImportPartsPost(
        @Path("id") id: String,
        @Body importPartDtoV2: ImportPartDtoV2
    ): SyncInformationV2

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
    suspend fun apiSyncIdStartExportPost(@Path("id") id: String): SyncInformationV2

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
    suspend fun apiSyncIdStartImportPost(@Path("id") id: String): Response<SyncInformationV2>

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
    suspend fun apiSyncPost(@Body starSyncRequestV2: StarSyncRequestV2): SyncInformationV2

    @Multipart
    @POST("api/sync/{syncId}/import-files")
    suspend fun importFiles(@Path("syncId") syncId: String, @Part filePart: MultipartBody.Part)

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
    @GET("api/sync/{syncId}/export-parts/{exportPartId}")
    suspend fun apiSyncSyncIdExportPartsExportPartIdGet(
        @Path("syncId") syncId: String,
        @Path("exportPartId") exportPartId: String
    ): String
}