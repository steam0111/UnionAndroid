package com.example.union_sync_api.entity

data class ReserveDetailSyncEntity(
    val reserveSyncEntity: ReserveSyncEntity,
    val locationSyncEntity: LocationSyncEntity?,
    val locationTypeSyncEntity: LocationTypeSyncEntity?,
    val molSyncEntity: EmployeeSyncEntity?,
    val structuralSyncEntity: StructuralSyncEntity?,
    val nomenclatureSyncEntity: NomenclatureSyncEntity?,
    val nomenclatureGroupSyncEntity: NomenclatureGroupSyncEntity?,
    val orderSyncEntity: OrderSyncEntity?,
    val receptionItemCategorySyncEntity: ReceptionItemCategorySyncEntity?,
    val balanceUnitSyncEntity: StructuralSyncEntity?
)