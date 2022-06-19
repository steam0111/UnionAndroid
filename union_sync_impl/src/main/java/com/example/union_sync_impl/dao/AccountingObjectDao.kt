package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.FullAccountingObject

@Dao
interface AccountingObjectDao {

    @RawQuery
    fun getAll(query: SupportSQLiteQuery): List<FullAccountingObject>

    @Query(
        "SELECT accounting_objects.*," +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id " +
                "WHERE accounting_objects.organizationId = :organizationId " +
                "AND accounting_objects.molId = :molId"
    )
    suspend fun getAllByParams(organizationId: String, molId: String): List<FullAccountingObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<AccountingObjectDb>)
}