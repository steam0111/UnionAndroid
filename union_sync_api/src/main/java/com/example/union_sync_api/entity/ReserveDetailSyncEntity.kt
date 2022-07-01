package com.example.union_sync_api.entity

data class ReserveDetailSyncEntity(
    val reserveSyncEntity: ReserveSyncEntity,
    val locationSyncEntity: LocationSyncEntity?,
    val molSyncEntity: EmployeeSyncEntity?,
    val businessUnitSyncEntity: OrganizationSyncEntity?,
    val structuralSubdivisionSyncEntity: DepartmentSyncEntity?,
    val nomenclatureSyncEntity: NomenclatureSyncEntity?,
    val nomenclatureGroupSyncEntity: NomenclatureGroupSyncEntity?,
    val orderSyncEntity: OrderSyncEntity?,
    val receptionItemCategorySyncEntity: ReceptionItemCategorySyncEntity?
)