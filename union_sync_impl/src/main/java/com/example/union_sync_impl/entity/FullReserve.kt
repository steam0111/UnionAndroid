package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb

class FullReserve(
    @Embedded
    val reserveDb: ReserveDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
    @Embedded(prefix = "mol_")
    val mol: EmployeeDb?,
    @Embedded(prefix = " businessUnit_")
    val businessUnit: OrganizationDb?,
    @Embedded(prefix = "structuralSubdivision_")
    val structuralSubdivision: DepartmentDb?,
    @Embedded(prefix = "nomenclature_")
    val nomenclature: NomenclatureDb?,
    @Embedded(prefix = "nomenclatureGroup_")
    val nomenclatureGroup: NomenclatureGroupDb?,
    @Embedded(prefix = "order_")
    val order: OrderDb?,
    @Embedded(prefix = "reception_item_category_")
    val receptionItemCategory: ReceptionItemCategoryDb?
)