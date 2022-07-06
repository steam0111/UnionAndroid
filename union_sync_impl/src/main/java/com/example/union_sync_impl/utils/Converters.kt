package com.example.union_sync_impl.utils

import androidx.room.TypeConverter
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
import com.example.union_sync_impl.entity.AccountingObjectStatusDb
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import java.lang.reflect.Type
import java.math.BigDecimal
import org.openapitools.client.infrastructure.BigDecimalAdapter

class Converters {

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    @TypeConverter
    fun stringToStringList(string: String?): List<String>? {
        val type: Type = newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return if (string != null) {
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
    fun toAccountingObjectStatus(value: String?): AccountingObjectStatusDb? {
        return if (value == null) null else {
            val type: Type =
                newParameterizedType(AccountingObjectStatusDb::class.java, String::class.java)
            val jsonAdapter: JsonAdapter<AccountingObjectStatusDb> = moshi.adapter(type)
            return jsonAdapter.fromJson(value)
        }
    }

    @TypeConverter
    fun fromAccountingObjectStatus(status: AccountingObjectStatusDb?): String? {
        return if (status == null) null else {
            val type: Type =
                newParameterizedType(AccountingObjectStatusDb::class.java, String::class.java)
            val jsonAdapter: JsonAdapter<AccountingObjectStatusDb> = moshi.adapter(type)
            return jsonAdapter.toJson(status)
        }
    }

    @TypeConverter
    fun toAccountingObjectId(value: String?): List<AccountingObjectInfoSyncEntity>? {
        return if (value == null) null else {
            val type: Type =
                newParameterizedType(List::class.java, AccountingObjectInfoSyncEntity::class.java)
            val jsonAdapter: JsonAdapter<List<AccountingObjectInfoSyncEntity>> = moshi.adapter(type)
            return jsonAdapter.fromJson(value)
        }
    }

    @TypeConverter
    fun fromAccountingObjectId(ids: List<AccountingObjectInfoSyncEntity>?): String? {
        return if (ids == null) null else {
            val type: Type =
                newParameterizedType(List::class.java, AccountingObjectInfoSyncEntity::class.java)
            val jsonAdapter: JsonAdapter<List<AccountingObjectInfoSyncEntity>> = moshi.adapter(type)
            return jsonAdapter.toJson(ids)
        }
    }
}