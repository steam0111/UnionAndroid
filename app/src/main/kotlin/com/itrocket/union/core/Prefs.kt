package com.itrocket.union.core

import android.content.Context

class Prefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

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

    companion object {
        private const val PREFS_NAME = "union_prefs"
        private const val KEY_SERVER_ADDRESS = "key server address"
        private const val KEY_PORT = "key port"
    }
}