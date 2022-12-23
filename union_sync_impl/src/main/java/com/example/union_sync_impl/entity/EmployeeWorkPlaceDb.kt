package com.example.union_sync_impl.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.example.union_sync_impl.entity.core.CatalogItemDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.structural.StructuralDb

@Entity(tableName = "employee_work_place")
class EmployeeWorkPlaceDb(
    id: String,
    override var catalogItemName: String,
    val employeeId: String,
    val comment: String?,
    val mainWorkPlace: Boolean
) : CatalogItemDb(
    id = id
)

class FullEmployeeWorkPlaceDb(
    @Embedded
    val employeeWorkPlaceDb: EmployeeWorkPlaceDb,
    @Embedded(prefix = "location_")
    val location: LocationDb?
)