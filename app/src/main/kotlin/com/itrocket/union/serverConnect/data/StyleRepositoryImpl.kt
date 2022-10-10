package com.itrocket.union.serverConnect.data

import android.content.Context
import com.itrocket.union.serverConnect.data.mappers.toDomain
import com.itrocket.union.serverConnect.domain.dependencies.StyleRepository
import com.itrocket.union.serverConnect.domain.entity.ColorDomain
import com.itrocket.union.theme.data.MediaRepositoryImpl
import java.io.File
import okhttp3.ResponseBody
import okio.buffer
import okio.sink
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openapitools.client.custom_api.StyleControllerApi

class StyleRepositoryImpl(
    private val applicationContext: Context
) : StyleRepository, KoinComponent {

    private val styleApi by inject<StyleControllerApi>()

    override suspend fun getStyleSettings(): ColorDomain {
        return styleApi.getStyleSettings().toDomain()
    }

    override suspend fun getLogoFile(): File {
        val response = styleApi.getLogoFile()
        val fileName = MediaRepositoryImpl.LOGO_FILE_NAME
        return convertResponseToFile(response, fileName)
    }

    override suspend fun getHeaderFile(): File {
        val response = styleApi.getHeaderFile()
        val fileName = MediaRepositoryImpl.HEADER_FILE_NAME
        return convertResponseToFile(response, fileName)
    }

    private suspend fun convertResponseToFile(response: ResponseBody, fileName: String): File {
        val cacheDir = applicationContext.cacheDir
        val file = File(cacheDir, fileName)
        file.sink().buffer().use {
            it.writeAll(response.source())
        }
        return file
    }
}