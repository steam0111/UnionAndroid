package com.example.union_sync_impl.utils

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import java.lang.reflect.Type


class Converters {

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String> {
        val type: Type = newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return jsonAdapter.fromJson(string) ?: emptyList()
    }

    @TypeConverter
    fun stringListToString(list: List<String>): String {
        val type: Type = newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return jsonAdapter.toJson(list)
    }
}