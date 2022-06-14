package com.itrocket.union.network

import com.squareup.moshi.Moshi
import java.io.IOException

data class HttpException(val error: String? = "") :
    IOException(error) {

    companion object {

        fun parseFromString(jsonString: String): HttpException? {
            val moshi = Moshi.Builder().build()
            return moshi.adapter(HttpException::class.java)
                .fromJson(jsonString)
        }
    }
}