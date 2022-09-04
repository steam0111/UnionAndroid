package org.openapitools.client.custom_api

import java.io.File
import okhttp3.ResponseBody
import org.openapitools.client.models.StyleSettingsDtoV2
import retrofit2.http.GET

interface StyleControllerApi {

    @GET("api/settings/styles/android")
    suspend fun getStyleSettings(): StyleSettingsDtoV2

    @GET("api/settings/styles/android/splash-logo")
    suspend fun getLogoFile(): ResponseBody

    @GET("api/settings/styles/android/header-logo")
    suspend fun getHeaderFile(): ResponseBody
}