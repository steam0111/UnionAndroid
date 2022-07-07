package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @RawQuery(observedEntities = [FullInventory::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<FullInventory>>

    @Query(
        "SELECT inventories.*," +
                "" +
                "organizations.id AS organizations_id, " +
                "organizations.catalogItemName AS organizations_catalogItemName, " +
                "organizations.name AS organizations_name, " +
                "organizations.actualAddress AS organizations_actualAddress, " +
                "organizations.legalAddress AS organizations_legalAddress, " +
                "" +
                "employees.id AS employees_id, " +
                "employees.catalogItemName AS employees_catalogItemName, " +
                "employees.firstname AS employees_firstname, " +
                "employees.lastname AS employees_lastname, " +
                "employees.patronymic AS employees_patronymic, " +
                "employees.organizationId AS employees_organizationId, " +
                "employees.number AS employees_number, " +
                "employees.nfc AS employees_nfc " +
                "FROM inventories " +
                "LEFT JOIN organizations ON inventories.organizationId = organizations.id " +
                "LEFT JOIN employees ON inventories.employeeId = employees.id " +
                "WHERE inventories.id = :id LIMIT 1 "
    )
    suspend fun getInventoryById(id: String): FullInventory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventoryDb: InventoryDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(inventories: List<InventoryDb>)

    @Update
    suspend fun update(inventoryDb: InventoryDb)

    @Query("SELECT COUNT(*) FROM inventories")
    suspend fun getInventoriesCount(): Int
}