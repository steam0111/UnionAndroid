package com.itrocket.union.manual

import com.example.union_sync_api.entity.OrganizationSyncEntity

fun OrganizationSyncEntity.toParamValue() = ParamValueDomain(
    id = id,
    value = name
)