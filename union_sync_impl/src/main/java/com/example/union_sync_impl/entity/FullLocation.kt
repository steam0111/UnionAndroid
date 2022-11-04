package com.example.union_sync_impl.entity

import androidx.room.Embedded
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

class FullLocation(
    @Embedded
    val locationDb: LocationDb,
    @Embedded(prefix = "locationTypes_")
    val locationTypeDb: LocationTypeDb?
)