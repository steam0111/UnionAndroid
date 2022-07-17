package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

class FullAccountingObject(
    @Embedded
    val accountingObjectDb: AccountingObjectDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
    @Embedded(prefix = "location_type_")
    val locationTypeDb: LocationTypeDb?,
    @Embedded(prefix = "mol_")
    val mol: EmployeeDb?,
    @Embedded(prefix = "exploiting_")
    val exploitingEmployee: EmployeeDb?,
    @Embedded(prefix = "organizations_")
    val organization: OrganizationDb?,
    @Embedded(prefix = "department_")
    val department: DepartmentDb?,
    @Embedded(prefix = "producer_")
    val producer: ProducerDb?,
    @Embedded(prefix = "equipment_type_")
    val equipmentType: EquipmentTypesDb?,
    @Embedded(prefix = "provider_")
    val provider: ProviderDb?,
    @Embedded(prefix = "branches_")
    val branch: BranchesDb?
)