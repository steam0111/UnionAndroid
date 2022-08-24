package com.example.union_sync_impl.data.mapper

import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.EmployeeSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.StructuralSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import com.example.union_sync_impl.entity.TransitDb
import com.example.union_sync_impl.utils.getMillisDateFromServerFormat
import com.example.union_sync_impl.utils.getStringDateFromMillis
import org.openapitools.client.models.TransitDtoV2

fun TransitDb.toTransitDtoV2() = TransitDtoV2(
    id = id,
    dateUpdate = getStringDateFromMillis(System.currentTimeMillis()),
    structuralUnitFromId = structuralFromId,
    structuralUnitToId = structuralToId,
    receivingId = receivingId,
    responsibleId = molId,
    vehicleId = vehicleId,
    locationFromId = locationFromId,
    locationToId = locationToId,
    creationDate = getStringDateFromMillis(creationDate),
    typeId = transitType,
    statusId = transitStatusId,
    code = code,
    deleted = false,
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun TransitDtoV2.toTransitDb() = TransitDb(
    id = id,
    updateDate = getMillisDateFromServerFormat(dateUpdate),
    structuralToId = structuralUnitToId,
    structuralFromId = structuralUnitFromId,
    receivingId = receivingId,
    molId = responsibleId,
    vehicleId = vehicleId,
    locationFromId = locationFromId,
    locationToId = locationToId,
    creationDate = getMillisDateFromServerFormat(creationDate),
    transitType = typeId ?: "TRANSIT_SENDING",
    transitStatus = extendedStatus?.id.orEmpty(),
    transitStatusId = statusId.orEmpty(),
    code = code,
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun TransitCreateSyncEntity.toTransitDb(id: String) = TransitDb(
    id = id,
    updateDate = System.currentTimeMillis(),
    structuralToId = structuralUnitToId,
    structuralFromId = structuralUnitFromId,
    receivingId = receivingId,
    molId = responsibleId,
    vehicleId = vehicleId,
    locationFromId = locationFromId,
    locationToId = locationToId,
    creationDate = System.currentTimeMillis(),
    transitType = transitType,
    transitStatus = transitStatus,
    transitStatusId = transitStatusId,
    code = code,
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun TransitUpdateSyncEntity.toTransitDb() = TransitDb(
    id = id,
    updateDate = System.currentTimeMillis(),
    structuralToId = structuralUnitToId,
    structuralFromId = structuralUnitFromId,
    receivingId = receivingId,
    molId = responsibleId,
    vehicleId = vehicleId,
    locationFromId = locationFromId,
    locationToId = locationToId,
    creationDate = System.currentTimeMillis(),
    transitType = transitType,
    transitStatus = transitStatus,
    transitStatusId = transitStatusId,
    code = code,
    userUpdated = userUpdated,
    userInserted = userInserted
)

fun TransitDb.toTransitSyncEntity(
    structuralFromSyncEntity: List<StructuralSyncEntity>?,
    structuralToSyncEntity: List<StructuralSyncEntity>?,
    accountingObjects: List<AccountingObjectSyncEntity>? = null,
    reserves: List<ReserveSyncEntity>? = null,
    locationFrom: List<LocationSyncEntity>?,
    locationTo: List<LocationSyncEntity>?,
    vehicle: List<LocationSyncEntity>?,
    mol: EmployeeSyncEntity?,
    receiving: EmployeeSyncEntity?
): TransitSyncEntity {
    return TransitSyncEntity(
        id = id,
        structuralFromSyncEntities = structuralFromSyncEntity,
        structuralToSyncEntities = structuralToSyncEntity,
        transitType = transitType,
        updateDate = updateDate,
        accountingObjects = accountingObjects.orEmpty(),
        reserves = reserves.orEmpty(),
        receiving = receiving,
        mol = mol,
        transitStatus = transitStatus,
        transitStatusId = transitStatusId,
        completionDate = completionDate,
        creationDate = creationDate,
        locationFrom = locationFrom,
        locationTo = locationTo,
        vehicle = vehicle,
        code = code,
        userUpdated = userUpdated,
        userInserted = userInserted
    )
}