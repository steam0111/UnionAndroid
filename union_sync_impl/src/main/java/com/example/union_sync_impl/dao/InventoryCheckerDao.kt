package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.FullInventoryCheckerDb
import com.example.union_sync_impl.entity.InventoryCheckerDb

@Dao
interface InventoryCheckerDao {

    @Query(
        "SELECT inventory_checker.*," +
                "" +
                "checker.id AS employee_id, " +
                "checker.catalogItemName AS employee_catalogItemName, " +
                "checker.firstname AS employee_firstname, " +
                "checker.lastname AS employee_lastname, " +
                "checker.patronymic AS employee_patronymic, " +
                "checker.number AS employee_number, " +
                "checker.nfc AS employee_nfc " +
                "" +
                "FROM inventory_checker " +
                "LEFT JOIN employees checker ON inventory_checker.employeeId = checker.id " +
                "WHERE inventoryId = :inventoryId AND cancel != 1"
    )
    fun getAll(inventoryId: String): List<FullInventoryCheckerDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(checkers: List<InventoryCheckerDb>)
}