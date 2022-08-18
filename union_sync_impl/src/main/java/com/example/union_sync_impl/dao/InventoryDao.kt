package com.example.union_sync_impl.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.union_sync_impl.entity.FullInventory
import com.example.union_sync_impl.entity.InventoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @RawQuery(observedEntities = [FullInventory::class])
    fun getAll(query: SupportSQLiteQuery): Flow<List<FullInventory>>

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
                "inventory_base.id AS inventory_base_id, " +
                "inventory_base.name AS inventory_base_name, " +
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
                "LEFT JOIN inventory_base ON inventories.inventoryBaseId = inventory_base.id " +
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