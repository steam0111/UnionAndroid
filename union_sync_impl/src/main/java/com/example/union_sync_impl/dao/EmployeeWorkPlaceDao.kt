package com.example.union_sync_impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.union_sync_impl.entity.EmployeeWorkPlaceDb
import com.example.union_sync_impl.entity.FullEmployeeWorkPlaceDb

@Dao
interface EmployeeWorkPlaceDao {

    @Query(
        "SELECT employee_work_place.*," +
                "" +
                "location.id AS location_id, " +
                "location.catalogItemName AS location_catalogItemName, " +
                "location.name AS location_name, " +
                "location.parentId AS location_parentId " +
                "" +
                "FROM employee_work_place " +
                "LEFT JOIN location ON employee_work_place.id = location.id " +
                "WHERE employee_work_place.employeeId = :id"
    )
    suspend fun getByEmployeeId(id: String): List<FullEmployeeWorkPlaceDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipmentTypes: List<EmployeeWorkPlaceDb>)
}