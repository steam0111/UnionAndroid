package com.itrocket.union.network

import com.squareup.moshi.Moshi
import java.io.IOException

data class NetworkException(val code: String? = "", override val message: String? = "") :
    IOException(message) {

    companion object {

        fun parseFromString(jsonString: String): NetworkException? {
            val moshi = Moshi.Builder().build()
            return moshi.adapter(NetworkException::class.java)
                .fromJson(jsonString)
        }
    }
}