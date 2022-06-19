package com.example.union_sync_impl.utils

import androidx.room.TypeConverter
import com.example.union_sync_api.entity.AccountingObjectStatus
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import java.lang.reflect.Type


class Converters {

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    @TypeConverter
    fun stringToStringList(string: String?): List<String>? {
        val type: Type = newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return if(string != null){
            jsonAdapter.fromJson(string)
        } else {
            null
        }
    }

    @TypeConverter
    fun stringListToString(list: List<String>?): String? {
        val type: Type = newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter: JsonAdapter<List<String>?> = moshi.adapter(type)
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toAccountingObjectStatus(value: String?): AccountingObjectStatus? {
        return if (value == null) null else {
            val type: Type = newParameterizedType(AccountingObjectStatus::class.java, String::class.java)
            val jsonAdapter: JsonAdapter<AccountingObjectStatus> = moshi.adapter(type)
            return jsonAdapter.fromJson(value)
        }
    }

    @TypeConverter
    fun fromAccountingObjectStatus(status: AccountingObjectStatus?): String? {
        return if (status == null) null else{
            val type: Type = newParameterizedType(AccountingObjectStatus::class.java, String::class.java)
            val jsonAdapter: JsonAdapter<AccountingObjectStatus> = moshi.adapter(type)
            return jsonAdapter.toJson(status)
        }
    }
}