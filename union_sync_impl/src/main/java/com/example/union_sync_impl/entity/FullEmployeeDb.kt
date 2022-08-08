package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.structural.StructuralDb

class FullEmployeeDb(
    @Embedded
    val employeeDb: EmployeeDb,
    @Embedded(prefix = "structural_")
    val structural: StructuralDb?
)