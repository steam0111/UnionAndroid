package com.itrocket.union.syncAll.data.mapper

import com.itrocket.union.syncAll.domain.entity.SyncAllDomain

fun List<Any>.map(): List<SyncAllDomain> = map {
    SyncAllDomain()
}