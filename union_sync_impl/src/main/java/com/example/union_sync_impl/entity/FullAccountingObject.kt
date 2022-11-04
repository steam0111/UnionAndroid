package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import com.example.union_sync_impl.entity.structural.StructuralDb

class FullAccountingObject(
    @Embedded
    val accountingObjectDb: AccountingObjectDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
    @Embedded(prefix = "locationTypes_")
    val locationTypesDb: LocationTypeDb?,
    @Embedded(prefix = "mol_")
    val mol: EmployeeDb?,
    @Embedded(prefix = "exploiting_")
    val exploitingEmployee: EmployeeDb?,
    @Embedded(prefix = "producer_")
    val producer: ProducerDb?,
    @Embedded(prefix = "equipment_type_")
    val equipmentType: EquipmentTypesDb?,
    @Embedded(prefix = "provider_")
    val provider: ProviderDb?,
    @Embedded(prefix = "structural_")
    val structuralDb: StructuralDb?
)