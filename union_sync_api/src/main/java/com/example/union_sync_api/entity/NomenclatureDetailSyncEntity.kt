package com.example.union_sync_api.entity

data class NomenclatureDetailSyncEntity(
    val nomenclature: NomenclatureSyncEntity,
    val nomenclatureGroup: NomenclatureGroupSyncEntity?
)