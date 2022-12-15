package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import com.example.union_sync_impl.entity.structural.StructuralDb

class FullReserve(
    @Embedded
    val reserveDb: ReserveDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
    @Embedded(prefix = "location_type_")
    val locationTypeDb: LocationTypeDb?,
    @Embedded(prefix = "mol_")
    val molDb: EmployeeDb?,
    @Embedded(prefix = "nomenclature_")
    val nomenclatureDb: NomenclatureDb?,
    @Embedded(prefix = "nomenclatureGroup_")
    val nomenclatureGroupDb: NomenclatureGroupDb?,
    @Embedded(prefix = "order_")
    val orderDb: OrderDb?,
    @Embedded(prefix = "receptionItemCategory_")
    val receptionItemCategoryDb: ReceptionItemCategoryDb?,
    @Embedded(prefix = "structural_")
    val structuralDb: StructuralDb?,
    @Embedded(prefix = "label_type_")
    val labelTypeDb: LabelTypeDb?
)