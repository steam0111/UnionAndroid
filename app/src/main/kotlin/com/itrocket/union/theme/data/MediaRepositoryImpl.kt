package com.itrocket.union.theme.data

import android.content.Context
import android.graphics.drawable.PictureDrawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.caverock.androidsvg.SVG
import com.itrocket.union.theme.domain.dependencies.MediaRepository
import com.itrocket.union.theme.domain.entity.Medias
import com.itrocket.utils.toPx
import java.io.File

class MediaRepositoryImpl(private val applicationContext: Context) : MediaRepository {

    override suspend fun saveMedias(logoFile: File?, headerFile: File?) {
        val directory = getWhiteLabelMediaDirectory()

        logoFile?.let {
            val newLogoFile = File(directory.absolutePath, LOGO_FILE_NAME)
            copyFileToInternalStorage(newFile = newLogoFile, oldFile = logoFile)
        }

        headerFile?.let {
            val newHeaderFile = File(directory.absolutePath, HEADER_FILE_NAME)
            copyFileToInternalStorage(newFile = newHeaderFile, oldFile = headerFile)
        }
    }

    override suspend fun getMedias(): Medias {
        val directory = getWhiteLabelMediaDirectory()
        val mediaFileList = directory.listFiles()?.toList()

        val logoFile = mediaFileList?.firstOrNull { it.name == LOGO_FILE_NAME }
        val headerFile = mediaFileList?.firstOrNull { it.name == HEADER_FILE_NAME }

        val logoBitmap = if (logoFile != null) {
            val svgLogo = SVG.getFromInputStream(logoFile.inputStream())
            svgLogo.setDocumentHeight(svgLogo.documentHeight.toInt().toPx.toString())
            svgLogo.setDocumentWidth(svgLogo.documentWidth.toInt().toPx.toString())
            PictureDrawable(
                svgLogo.renderToPicture()
            ).toBitmap()
        } else {
            null
        }

        val headerBitmap = if (headerFile != null) {
            val svgHeader = SVG.getFromInputStream(headerFile.inputStream())
            svgHeader.setDocumentHeight(svgHeader.documentHeight.toInt().toPx.toString())
            svgHeader.setDocumentWidth(svgHeader.documentWidth.toInt().toPx.toString())
            PictureDrawable(svgHeader.renderToPicture()).toBitmap()
        } else {
            null
        }

        return Medias(logo = logoBitmap?.asImageBitmap(), header = headerBitmap?.asImageBitmap())
    }

    private suspend fun copyFileToInternalStorage(newFile: File, oldFile: File) {
        newFile.createNewFile()
        newFile.outputStream().use {
            it.write(oldFile.readBytes())
        }
    }

    private fun getWhiteLabelMediaDirectory(): File {
        val directory = File(applicationContext.filesDir, WHITE_LABEL_MEDIAS)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

    companion object {
        private const val WHITE_LABEL_MEDIAS = "whiteLabelMedias"
        const val HEADER_FILE_NAME = "headerMedia.svg"
        const val LOGO_FILE_NAME = "logoMedia.svg"
    }
}