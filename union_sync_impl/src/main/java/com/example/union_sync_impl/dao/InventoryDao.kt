package com.example.union_sync_impl.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @RawQuery(observedEntities = [FullInventory::class])
    suspend fun getAll(query: SupportSQLiteQuery): List<FullInventory>

    @RawQuery
    suspend fun getAllSimpled(query: SupportSQLiteQuery): List<InventoryDb>

    @RawQuery
    suspend fun getCount(query: SupportSQLiteQuery): Long

    @Query(
        "SELECT inventories.*," +
                "" +
                "structural.id AS structural_id, " +
                "structural.catalogItemName AS structural_catalogItemName, " +
                "structural.name AS structural_name, " +
                "structural.parentId AS structural_parentId, " +
                "" +
                "employees.id AS employees_id, " +
                "employees.catalogItemName AS employees_catalogItemName, " +
                "employees.firstname AS employees_firstname, " +
                "employees.lastname AS employees_lastname, " +
                "employees.patronymic AS employees_patronymic, " +
                "employees.structuralId AS employees_structuralId, " +
                "employees.number AS employees_number, " +
                "employees.nfc AS employees_nfc " +
                "FROM inventories " +
                "LEFT JOIN employees ON inventories.employeeId = employees.id " +
                "LEFT JOIN structural ON inventories.structuralId = structural.id " +
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

    @Query("DELETE FROM inventories")
    suspend fun clearAll()
}