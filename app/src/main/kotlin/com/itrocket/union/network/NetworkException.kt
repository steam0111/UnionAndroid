package com.itrocket.union.network

import android.util.Log
import com.squareup.moshi.Moshi
import java.io.IOException

data class NetworkException(val code: String? = "", override val message: String? = "") :
    IOException(message) {

    companion object {

        fun parseFromString(jsonString: String): NetworkException? {
            return try {
                val moshi = Moshi.Builder().build()
                moshi.adapter(NetworkException::class.java)
                    .fromJson(jsonString)
            } catch (t: Throwable) {
                null
            }
        }
    }
}