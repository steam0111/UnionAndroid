package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.AccountingObjectDb
import com.example.union_sync_impl.entity.FullAccountingObject

@Dao
interface AccountingObjectDao {
    @Query(
        "SELECT accounting_objects.*," +
                "" +
                "location.id AS locations_id, " +
                "location.catalogItemName AS locations_catalogItemName, " +
                "location.name AS locations_name, " +
                "location.parentId AS locations_parentId " +
                "" +
                "FROM accounting_objects " +
                "LEFT JOIN location ON accounting_objects.locationId = location.id"
    )
    suspend fun getAll(): List<FullAccountingObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<AccountingObjectDb>)
}