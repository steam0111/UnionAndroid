package com.example.union_sync_impl.data

import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.data.TransitSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_api.entity.TransitCreateSyncEntity
import com.example.union_sync_api.entity.TransitSyncEntity
import com.example.union_sync_api.entity.TransitUpdateReservesSyncEntity
import com.example.union_sync_api.entity.TransitUpdateSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.TransitAccountingObjectRecordDao
import com.example.union_sync_impl.dao.TransitDao
import com.example.union_sync_impl.dao.TransitRemainsRecordDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.dao.sqlTransitQuery
import com.example.union_sync_impl.dao.sqlTransitRecordQuery
import com.example.union_sync_impl.dao.sqlTransitRemainsRecordQuery
import com.example.union_sync_impl.data.mapper.toDocumentReserveCount
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.data.mapper.toTransitDb
import com.example.union_sync_impl.data.mapper.toTransitSyncEntity
import com.example.union_sync_impl.entity.TransitAccountingObjectRecordDb
import com.example.union_sync_impl.entity.TransitRemainsRecordDb
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransitSyncApiImpl(
    private val transitDao: TransitDao,
    private val accountingObjectDao: AccountingObjectDao,
    private val reserveDao: ReserveDao,
    private val locationSyncApi: LocationSyncApi,
    private val transitAccountingObjectRecordDao: TransitAccountingObjectRecordDao,
    private val transitRemainsRecordDao: TransitRemainsRecordDao,
    private val structuralSyncApi: StructuralSyncApi
) : TransitSyncApi {

    override suspend fun createTransit(transitCreateSyncEntity: TransitCreateSyncEntity): String {
        val transitId = UUID.randomUUID().toString()
        transitDao.insert(transitCreateSyncEntity.toTransitDb(transitId))
        updateTransitRecords(
            accountingObjectIds = transitCreateSyncEntity.accountingObjectsIds.orEmpty(),
            transitId = transitId,
            userUpdated = transitCreateSyncEntity.userUpdated
        )
        updateRemainsTransitRecords(
            remainIds = transitCreateSyncEntity.reservesIds.orEmpty(),
            transitId = transitId,
            userUpdated = transitCreateSyncEntity.userUpdated
        )
        return transitId
    }

    override suspend fun getAllTransit(
        textQuery: String?,
        responsibleId: String?,
        structuralFromId: String?,
        structuralToId: String?,
        receivingId: String?
    ): Flow<List<TransitSyncEntity>> {
        return transitDao.getAll(
            sqlTransitQuery(
                textQuery = textQuery,
                molId = responsibleId,
                receivingId = receivingId,
                structuralFromId = structuralFromId,
                structuralToId = structuralToId
            )
        ).map { transits ->
            transits.map { transit ->

                val structuralToSyncEntity =
                    listOfNotNull(structuralSyncApi.getStructuralById(transit.transitDb.structuralToId))
                val structuralFromSyncEntity =
                    listOfNotNull(structuralSyncApi.getStructuralById(transit.transitDb.structuralFromId))

                transit.transitDb.toTransitSyncEntity(
                    locationFrom = locationSyncApi.getLocationById(transit.transitDb.locationFromId),
                    locationTo = locationSyncApi.getLocationById(transit.transitDb.locationToId),
                    receiving = transit.receivingDb?.toSyncEntity(),
                    mol = transit.molDb?.toSyncEntity(),
                    vehicle = locationSyncApi.getLocationById(transit.transitDb.vehicleId),
                    accountingObjects = listOf(),
                    structuralToSyncEntity = structuralToSyncEntity,
                    structuralFromSyncEntity = structuralFromSyncEntity,
                )
            }
        }
    }

    override suspend fun getTransitById(id: String): TransitSyncEntity {
        val fullTransit = transitDao.getTransitById(id)
        val accountingObjectIds =
            transitAccountingObjectRecordDao.getAll(sqlTransitRecordQuery(id))
                .map { it.accountingObjectId }
        val accountingObjects: List<AccountingObjectSyncEntity> =
            accountingObjectDao.getAll(
                sqlAccountingObjectQuery(
                    accountingObjectsIds = accountingObjectIds.filterNotNull()
                )
            ).map {
                val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
                it.toSyncEntity(locationSyncEntity = location)
            }

        val reserveRecords =
            transitRemainsRecordDao.getAll((sqlTransitRemainsRecordQuery(transitId = id)))

        var reserves: List<ReserveSyncEntity> =
            reserveDao.getAll(sqlReserveQuery(reservesIds = reserveRecords.map { it.remainId }
                .filterNotNull()))
                .map { it.toSyncEntity() }

        reserves = reserves.map { reserve ->
            val documentReserveCount = reserveRecords.find { it.remainId == reserve.id }
            if (documentReserveCount != null) {
                reserve.copy(count = documentReserveCount.count)
            } else {
                reserve
            }
        }

        val structuralFromIds =
            structuralSyncApi.getStructuralFullPath(
                fullTransit.transitDb.structuralFromId,
                mutableListOf()
            ).orEmpty()

        val structuralToIds =
            structuralSyncApi.getStructuralFullPath(
                fullTransit.transitDb.structuralToId,
                mutableListOf()
            ).orEmpty()

        return fullTransit.transitDb.toTransitSyncEntity(
            structuralFromSyncEntity = structuralFromIds,
            structuralToSyncEntity = structuralToIds,
            accountingObjects = accountingObjects,
            reserves = reserves,
            locationFrom = locationSyncApi.getLocationById(fullTransit.transitDb.locationFromId),
            locationTo = locationSyncApi.getLocationById(fullTransit.transitDb.locationToId),
            receiving = fullTransit.receivingDb?.toSyncEntity(),
            mol = fullTransit.molDb?.toSyncEntity(),
            vehicle = locationSyncApi.getLocationById(fullTransit.transitDb.vehicleId),
        )
    }

    private suspend fun updateTransitRecords(
        accountingObjectIds: List<String>,
        transitId: String,
        userUpdated: String?
    ) {
        val existRecords = transitAccountingObjectRecordDao.getAll(
            sqlTransitRecordQuery(
                transitId = transitId,
                accountingObjectIds = accountingObjectIds
            )
        )
        val newRecords = accountingObjectIds.map { accountingObjectId ->
            val existRecord = existRecords.find { it.accountingObjectId == accountingObjectId }
            TransitAccountingObjectRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                accountingObjectId = accountingObjectId,
                transitId = transitId,
                insertDate = existRecord?.insertDate,
                updateDate = System.currentTimeMillis(),
                userInserted = existRecord?.userInserted ?: userUpdated,
                userUpdated = userUpdated
            )
        }
        transitAccountingObjectRecordDao.insertAll(newRecords)
    }

    private suspend fun updateRemainsTransitRecords(
        remainIds: List<ReserveCountSyncEntity>,
        transitId: String,
        userUpdated: String?
    ) {
        val existRecords = transitRemainsRecordDao.getAll(
            sqlTransitRemainsRecordQuery(
                transitId = transitId,
                remainIds = remainIds.map { it.id }
            )
        )
        val newRecords = remainIds.map { remain ->
            val existRecord = existRecords.find { it.remainId == remain.id }
            TransitRemainsRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                remainId = remain.id,
                transitId = transitId,
                updateDate = System.currentTimeMillis(),
                insertDate = existRecord?.insertDate,
                count = remain.count,
                userUpdated = userUpdated,
                userInserted = existRecord?.userInserted ?: userUpdated
            )
        }
        transitRemainsRecordDao.insertAll(newRecords)
    }

    override suspend fun updateTransit(transitUpdateSyncEntity: TransitUpdateSyncEntity) {
        transitDao.update(transitUpdateSyncEntity.toTransitDb())
        updateTransitRecords(
            accountingObjectIds = transitUpdateSyncEntity.accountingObjectsIds.orEmpty(),
            transitId = transitUpdateSyncEntity.id,
            userUpdated = transitUpdateSyncEntity.userUpdated
        )
        updateRemainsTransitRecords(
            remainIds = transitUpdateSyncEntity.reservesIds.orEmpty(),
            transitId = transitUpdateSyncEntity.id,
            userUpdated = transitUpdateSyncEntity.userUpdated
        )
    }

    override suspend fun updateTransitReserves(updateTransitReservesSyncEntity: TransitUpdateReservesSyncEntity) {
        updateRemainsTransitRecords(
            remainIds = updateTransitReservesSyncEntity.reservesIds,
            transitId = updateTransitReservesSyncEntity.id,
            userUpdated = updateTransitReservesSyncEntity.userUpdated
        )
    }

    override suspend fun updateTransitReservesCount(transitReserveCounts: List<ReserveCountSyncEntity>) {
        transitRemainsRecordDao.update(transitReserveCounts.map { it.toDocumentReserveCount() })
    }
}