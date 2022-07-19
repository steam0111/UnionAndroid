package com.itrocket.union.inventoryCreate.data.mapper

import com.example.union_sync_api.entity.InventorySyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.location.data.mapper.toLocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain

fun List<InventorySyncEntity>.map(): List<InventoryCreateDomain> = map {
    it.map()
}

fun InventorySyncEntity.map(): InventoryCreateDomain =
    InventoryCreateDomain(
        number = code.orEmpty(),
        date = date,
        accountingObjects = accountingObjects.map(),
        documentInfo = buildList {
            add(
                ParamDomain(
                    organizationSyncEntity?.id,
                    organizationSyncEntity?.name.orEmpty(),
                    ManualType.ORGANIZATION
                )
            )
            mol?.let { mol ->
                add(ParamDomain(mol.id, "${mol.firstname} ${mol.lastname}", ManualType.MOL))
            }
            locationSyncEntities?.let { locationSyncEntities ->
                add(
                    LocationParamDomain(
                        locations = locationSyncEntities.map { it.toLocationDomain() })
                )
            }
        },
        inventoryStatus = InventoryStatus.valueOf(inventoryStatus)
    )