package com.itrocket.union.branches.data.mapper

import com.example.union_sync_api.entity.BranchSyncEntity
import com.itrocket.union.branches.domain.entity.BranchesDomain

fun List<BranchSyncEntity>.map(): List<BranchesDomain> = map {
    BranchesDomain(
        id = it.id,
        catalogItemName = it.catalogItemName,
        name = it.name.orEmpty(),
        code = it.code.orEmpty()
    )
}