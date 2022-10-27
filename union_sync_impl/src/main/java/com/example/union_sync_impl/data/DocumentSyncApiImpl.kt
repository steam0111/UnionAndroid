package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.EnumsSyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.EnumType
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveCountSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.dao.sqlDocumentsQuery
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDocumentSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DocumentSyncApiImpl(
    private val documentDao: DocumentDao,
    private val structuralSyncApi: StructuralSyncApi,
    private val accountingObjectDao: AccountingObjectDao,
    private val reserveDao: ReserveDao,
    private val locationSyncApi: LocationSyncApi,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao,
    private val enumsApi: EnumsSyncApi
) : DocumentSyncApi {
    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String {
        val documentId = UUID.randomUUID().toString()
        documentDao.insert(documentCreateSyncEntity.toDocumentDb(documentId))
        updateActionRecords(
            accountingObjectIds = documentCreateSyncEntity.accountingObjectsIds.orEmpty(),
            actionId = documentId,
            userUpdated = documentCreateSyncEntity.userUpdated
        )
        updateRemainsActionRecords(
            remainIds = documentCreateSyncEntity.reservesIds.orEmpty(),
            actionId = documentId,
            userUpdated = documentCreateSyncEntity.userUpdated
        )
        return documentId
    }

    override suspend fun getDocumentsCount(
        type: String,
        textQuery: String?,
        molId: String?,
        exploitingId: String?,
        structuralFromId: String?,
        structuralToId: String?
    ): Long {
        return documentDao.getCount(
            sqlDocumentsQuery(
                textQuery = textQuery,
                molId = molId,
                exploitingId = exploitingId,
                structuralFromId = structuralFromId,
                structuralToId = structuralToId,
                isFilterCount = true,
                type = type
            )
        )
    }

    override suspend fun getDocumentsByType(
        type: String,
        textQuery: String?,
        molId: String?,
        exploitingId: String?,
        structuralFromId: String?,
        structuralToId: String?
    ): Flow<List<DocumentSyncEntity>> {
        return documentDao.getAll(
            sqlDocumentsQuery(
                textQuery = textQuery,
                molId = molId,
                exploitingId = exploitingId,
                structuralFromId = structuralFromId,
                structuralToId = structuralToId,
                type = type
            )
        ).map { documents ->
            documents.map { document ->
                val structuralToSyncEntity =
                    listOfNotNull(structuralSyncApi.getStructuralById(document.documentDb.structuralToId))
                val structuralFromSyncEntity =
                    listOfNotNull(structuralSyncApi.getStructuralById(document.documentDb.structuralFromId))

                val balanceUnitFrom = structuralFromSyncEntity.lastOrNull { it.balanceUnit }
                val balanceUnitTo = structuralToSyncEntity.lastOrNull { it.balanceUnit }

                document.documentDb.toDocumentSyncEntity(
                    mol = document.molDb?.toSyncEntity(),
                    exploiting = document.exploitingDb?.toSyncEntity(),
                    accountingObjects = listOf(),
                    locationFrom = listOfNotNull(locationSyncApi.getLocationById(document.documentDb.locationFromId)),
                    locationTo = listOfNotNull(locationSyncApi.getLocationById(document.documentDb.locationToId)),
                    structuralToSyncEntity = structuralToSyncEntity,
                    structuralFromSyncEntity = structuralFromSyncEntity,
                    balanceUnitFrom = listOfNotNull(balanceUnitFrom),
                    balanceUnitTo = listOfNotNull(balanceUnitTo)
                )
            }
        }
    }


    override suspend fun getDocumentById(id: String): DocumentSyncEntity {
        val fullDocument = documentDao.getDocumentById(id)
        val accountingObjectIds =
            actionRecordDao.getAll(sqlActionRecordQuery(id)).map { it.accountingObjectId }
        val accountingObjects: List<AccountingObjectSyncEntity> =
            accountingObjectDao.getAll(
                sqlAccountingObjectQuery(
                    accountingObjectsIds = accountingObjectIds
                )
            ).map {
                val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
                it.toSyncEntity(locationSyncEntity = listOfNotNull(location))
            }

        val reserveRecords =
            actionRemainsRecordDao.getAll((sqlActionRemainsRecordQuery(actionId = id)))

        var reserves: List<ReserveSyncEntity> =
            reserveDao.getAll(sqlReserveQuery(reservesIds = reserveRecords.map { it.remainId }))
                .map {
                    val locationSyncEntity = it.locationDb?.toLocationSyncEntity(it.locationTypeDb)
                    it.toSyncEntity(listOfNotNull(locationSyncEntity))
                }

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
                fullDocument.documentDb.structuralFromId,
                mutableListOf()
            ).orEmpty()

        val structuralToIds =
            structuralSyncApi.getStructuralFullPath(
                fullDocument.documentDb.structuralToId,
                mutableListOf()
            ).orEmpty()

        val balanceUnitFrom = structuralFromIds.lastOrNull { it.balanceUnit }
        val balanceUnitTo = structuralToIds.lastOrNull { it.balanceUnit }

        val balanceUnitFromFullPath =
            structuralSyncApi.getStructuralFullPath(balanceUnitFrom?.id, mutableListOf())
        val balanceUnitToFullPath =
            structuralSyncApi.getStructuralFullPath(balanceUnitTo?.id, mutableListOf())

        val actionBase = enumsApi.getByCompoundId(
            enumType = EnumType.ACTION_BASE,
            id = fullDocument.documentDb.actionBaseId
        )

        return fullDocument.documentDb.toDocumentSyncEntity(
            mol = fullDocument.molDb?.toSyncEntity(),
            exploiting = fullDocument.exploitingDb?.toSyncEntity(),
            accountingObjects = accountingObjects,
            reserves = reserves,
            locationFrom = locationSyncApi.getLocationFullPath(fullDocument.documentDb.locationFromId),
            locationTo = locationSyncApi.getLocationFullPath(fullDocument.documentDb.locationToId),
            structuralToSyncEntity = structuralToIds,
            structuralFromSyncEntity = structuralFromIds,
            actionBase = actionBase,
            balanceUnitFrom = balanceUnitFromFullPath,
            balanceUnitTo = balanceUnitToFullPath
        )
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        documentDao.update(documentUpdateSyncEntity.toDocumentDb())
        updateActionRecords(
            accountingObjectIds = documentUpdateSyncEntity.accountingObjectsIds.orEmpty(),
            actionId = documentUpdateSyncEntity.id,
            userUpdated = documentUpdateSyncEntity.userUpdated
        )
        updateRemainsActionRecords(
            remainIds = documentUpdateSyncEntity.reservesIds.orEmpty(),
            actionId = documentUpdateSyncEntity.id,
            userUpdated = documentUpdateSyncEntity.userUpdated
        )
    }

    override suspend fun updateDocumentReserves(documentUpdateReservesSyncEntity: DocumentUpdateReservesSyncEntity) {
        updateRemainsActionRecords(
            remainIds = documentUpdateReservesSyncEntity.reservesIds,
            actionId = documentUpdateReservesSyncEntity.id,
            userUpdated = documentUpdateReservesSyncEntity.userUpdated
        )
    }

    private suspend fun updateActionRecords(
        accountingObjectIds: List<String>,
        actionId: String,
        userUpdated: String?
    ) {
        val records =
            actionRecordDao.getAll(sqlActionRecordQuery(actionId = actionId)).map {
                it.copy(cancel = !accountingObjectIds.contains(it.accountingObjectId))
            }
        val existRecords = records.filter { accountingObjectIds.contains(it.accountingObjectId) }
        val newRecords = accountingObjectIds.map { accountingObjectId ->
            val existRecord = existRecords.find { it.accountingObjectId == accountingObjectId }
            ActionRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                accountingObjectId = accountingObjectId,
                actionId = actionId,
                insertDate = existRecord?.insertDate ?: System.currentTimeMillis(),
                updateDate = System.currentTimeMillis(),
                userInserted = existRecord?.userInserted ?: userUpdated,
                userUpdated = userUpdated,
                cancel = false
            )
        }
        actionRecordDao.insertAll(newRecords)

        val removedAccountingObjects = records.filter { it.cancel == true }
        actionRecordDao.insertAll(removedAccountingObjects)
    }

    private suspend fun updateRemainsActionRecords(
        remainIds: List<ReserveCountSyncEntity>,
        actionId: String,
        userUpdated: String?
    ) {
        val mappedRemainIds = remainIds.map { it.id }
        val records =
            actionRemainsRecordDao.getAll(sqlActionRemainsRecordQuery(actionId = actionId)).map {
                it.copy(cancel = !mappedRemainIds.contains(it.remainId))
            }
        val existRecords = records.filter { mappedRemainIds.contains(it.remainId) }
        val newRecords = remainIds.map { remain ->
            val existRecord = existRecords.find { it.remainId == remain.id }
            ActionRemainsRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                remainId = remain.id,
                actionId = actionId,
                updateDate = System.currentTimeMillis(),
                insertDate = existRecord?.insertDate ?: System.currentTimeMillis(),
                count = remain.count,
                userUpdated = userUpdated,
                userInserted = existRecord?.userInserted ?: userUpdated,
                cancel = false
            )
        }
        actionRemainsRecordDao.insertAll(newRecords)

        val removedRecords = records.filter { it.cancel == true }
        actionRemainsRecordDao.insertAll(removedRecords)
    }

    private suspend fun List<String?>?.getLocations(): List<LocationSyncEntity> {
        return this?.let { locationSyncApi.getLocationsByIds(it) }.orEmpty()
    }
}