package com.itrocket.union.inventoryCreate.data.mapper

import com.example.union_sync_api.entity.InventorySyncEntity
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

fun List<InventorySyncEntity>.map(): List<InventoryCreateDomain> = map {
    it.map()
}

fun InventorySyncEntity.map(): InventoryCreateDomain =
    InventoryCreateDomain(
        number = id,
        date = date,
        accountingObjects = listOf(),
        documentInfo = listOf(
            ParamDomain(
                organizationSyncEntity.id,
                organizationSyncEntity.name,
                ManualType.ORGANIZATION
            ),
            ParamDomain(mol.id, "${mol.firstname} ${mol.lastname}", ManualType.MOL),
            LocationParamDomain(
                locationSyncEntities.map { it.id },
                values = locationSyncEntities.map { it.name })
        )
    )