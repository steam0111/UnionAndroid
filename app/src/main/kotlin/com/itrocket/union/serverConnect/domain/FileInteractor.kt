package com.itrocket.union.serverConnect.domain

import android.content.Context
import android.net.Uri
import com.itrocket.core.base.CoreDispatchers
import com.itrocket.union.R
import kotlinx.coroutines.withContext
import java.io.FileOutputStream

typealias isSuccessful = Boolean

class FileInteractor(
    private val context: Context, private val coreDispatchers: CoreDispatchers
) {
    suspend fun writeTextToFile(uri: Uri, text: String): isSuccessful =
        withContext(coreDispatchers.io) {
            val contentResolver = context.contentResolver

            try {
                contentResolver.openFileDescriptor(uri, "w")?.use {
                    FileOutputStream(it.fileDescriptor).use { fileOutputStream ->
                        fileOutputStream.write(text.toByteArray())
                    }
                }
                return@withContext true
            } catch (throwable: Throwable) {
                return@withContext false
            }
        }

    suspend fun readTextFromFileAtTime(uri: Uri): String = withContext(coreDispatchers.io) {
        val contentResolver = context.contentResolver

        contentResolver.openInputStream(uri)?.use { inputStream ->
            return@withContext String(inputStream.readBytes())
        }

        throw IllegalStateException(context.getString(R.string.app_file_data_parse_error_file))
    }
}