package com.itrocket.union.network

import com.squareup.moshi.Moshi
import java.io.IOException

data class HttpException(val error: String? = "", val path: String? = "") : IOException() {

    fun getFormattedMessage(): String? =
        formatError(error, path)

    private fun formatError(
        error: String? = "",
        path: String? = ""
    ): String? {
        if (error.isNullOrBlank() || path.isNullOrBlank()) return null

        return "error:$error " +
                "path:$path"
    }

    companion object {

        fun parseFromString(jsonString: String): HttpException? {
            val moshi = Moshi.Builder().build()
            return moshi.adapter(HttpException::class.java)
                .fromJson(jsonString)
        }
    }
}