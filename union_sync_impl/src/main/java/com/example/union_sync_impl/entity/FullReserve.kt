package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb

class FullReserve(
    @Embedded
    val reserveDb: ReserveDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb?,
    @Embedded(prefix = " businessUnit_")
    val businessUnitDb: OrganizationDb?,
    @Embedded(prefix = "structuralSubdivision_")
    val structuralSubdivisionDb: DepartmentDb?,
    @Embedded(prefix = "nomenclature_")
    val nomenclatureDb: NomenclatureDb?,
    @Embedded(prefix = "nomenclatureGroup_")
    val nomenclatureGroupDb: NomenclatureGroupDb?,
    @Embedded(prefix = "order_")
    val orderDb: OrderDb?,
    @Embedded(prefix = "reception_item_category_")
    val receptionItemCategoryDb: ReceptionItemCategoryDb?
)