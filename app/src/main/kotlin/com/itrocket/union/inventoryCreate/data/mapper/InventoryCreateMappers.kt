package com.itrocket.union.inventoryCreate.data.mapper

import com.example.union_sync_api.entity.InventorySyncEntity
import com.itrocket.union.accountingObjects.data.mapper.map
import com.itrocket.union.inventories.domain.entity.InventoryStatus
import com.itrocket.union.inventoryCreate.domain.entity.InventoryCreateDomain
import com.itrocket.union.location.data.mapper.toLocationDomain
import com.itrocket.union.manual.LocationParamDomain
import com.itrocket.union.manual.ManualType
import com.itrocket.union.manual.ParamDomain
import com.itrocket.union.manual.StructuralParamDomain
import com.itrocket.union.structural.data.mapper.toStructuralDomain

fun List<InventorySyncEntity>.map(): List<InventoryCreateDomain> = map {
    it.map()
}

fun InventorySyncEntity.map(): InventoryCreateDomain =

    InventoryCreateDomain(
        id = id,
        number = code.orEmpty(),
        creationDate = creationDate,
        accountingObjects = accountingObjects.map(),
        documentInfo = buildList {
            add(
                StructuralParamDomain(manualType = ManualType.STRUCTURAL, structurals = buildList {
                    structuralSyncEntities?.let {
                        addAll(it.map { it.toStructuralDomain() })
                    }
                })
            )
            add(
                StructuralParamDomain(
                    manualType = ManualType.BALANCE_UNIT,
                    structurals = balanceUnit.map { it.toStructuralDomain() })
            )
            add(
                ParamDomain(
                    id = mol?.id,
                    value = mol?.fullName.orEmpty(),
                    type = ManualType.MOL_IN_STRUCTURAL
                )
            )
            add(
                LocationParamDomain(
                    manualType = ManualType.LOCATION_INVENTORY,
                    locations = locationSyncEntities?.map { it.toLocationDomain() }.orEmpty()
                )
            )

            add(
                ParamDomain(
                    id = inventoryBaseSyncEntity?.id,
                    value = inventoryBaseSyncEntity?.name.orEmpty(),
                    type = ManualType.INVENTORY_BASE
                )
            )
            add(
                ParamDomain(
                    value = checkers?.joinToString(", ") { it.employee.catalogItemName }.orEmpty(),
                    type = ManualType.INVENTORY_CHECKER,
                    isClickable = false,
                    isFilter = false
                )
            )
        },
        inventoryStatus = InventoryStatus.valueOf(inventoryStatus),
        userInserted = userInserted,
        userUpdated = userUpdated
    )