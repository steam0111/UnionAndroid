package com.example.union_sync_impl.entity

import androidx.room.Embedded

class FullRegionDb(
    @Embedded
    val regionDb: RegionDb,
    @Embedded(prefix = "organizations_")
    val organizationDb: OrganizationDb?
)