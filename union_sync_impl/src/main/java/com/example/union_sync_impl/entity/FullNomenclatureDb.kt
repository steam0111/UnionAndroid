package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullNomenclatureDb(
    @Embedded
    val nomenclatureDb: NomenclatureDb,
    @Embedded(prefix = "groups_")
    val nomenclatureGroupDb: NomenclatureGroupDb?,
)