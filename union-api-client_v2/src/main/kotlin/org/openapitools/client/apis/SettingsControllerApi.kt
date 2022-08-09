package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody

import org.openapitools.client.models.ServerInformationResponseV2

interface SettingsControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [ServerInformationResponseV2]
     */
    @GET("api/server-information")
    suspend fun apiServerInformationGet(): Response<ServerInformationResponseV2>

}
