package com.itrocket.union.core

import android.content.Context

class Prefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun getFullServerAddress(): String {
        return if (!serverAddress.isNullOrBlank()) {
            "$serverAddress:$port"
        } else {
            DEFAULT_SERVER_ADDRESS
        }
    }

    var serverAddress: String?
        get() = sharedPreferences.getString(KEY_SERVER_ADDRESS, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_SERVER_ADDRESS, value).apply()
        }

    var port: String?
        get() = sharedPreferences.getString(KEY_PORT, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_PORT, value).apply()
        }

    var accessToken: String?
        get() = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, value).apply()
        }

    var refreshToken: String?
        get() = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }

    companion object {
        private const val DEFAULT_SERVER_ADDRESS = "http://dev.union-eam.ru:8686"

        private const val PREFS_NAME = "union_prefs"
        private const val KEY_SERVER_ADDRESS = "key server address"
        private const val KEY_PORT = "key port"
        private const val KEY_ACCESS_TOKEN = "key access token"
        private const val KEY_REFRESH_TOKEN = "key refresh token"
    }
}