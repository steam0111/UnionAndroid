package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb

class FullAccountingObject(
    @Embedded
    val accountingObjectDb: AccountingObjectDb,
    @Embedded(prefix = "locations_")
    val locationDb: LocationDb?,
)