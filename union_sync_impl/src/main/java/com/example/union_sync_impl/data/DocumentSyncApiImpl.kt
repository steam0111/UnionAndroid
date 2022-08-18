package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentReserveCountSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateReservesSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_api.entity.ReserveSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.ActionRecordDao
import com.example.union_sync_impl.dao.ActionRemainsRecordDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.dao.sqlDocumentsQuery
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDocumentSyncEntity
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class DocumentSyncApiImpl(
    private val documentDao: DocumentDao,
    private val locationDao: LocationDao,
    private val structuralSyncApi: StructuralSyncApi,
    private val accountingObjectDao: AccountingObjectDao,
    private val reserveDao: ReserveDao,
    private val locationSyncApi: LocationSyncApi,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao
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

                document.documentDb.toDocumentSyncEntity(
                    mol = document.molDb?.toSyncEntity(),
                    exploiting = document.exploitingDb?.toSyncEntity(),
                    accountingObjects = listOf(),
                    locationFrom = locationSyncApi.getLocationById(document.documentDb.locationFromId),
                    locationTo = locationSyncApi.getLocationById(document.documentDb.locationToId),
                    structuralToSyncEntity = structuralToSyncEntity,
                    structuralFromSyncEntity = structuralFromSyncEntity,
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
                it.toSyncEntity(locationSyncEntity = location)
            }

        val reserveRecords =
            actionRemainsRecordDao.getAll((sqlActionRemainsRecordQuery(actionId = id)))

        var reserves: List<ReserveSyncEntity> =
            reserveDao.getAll(sqlReserveQuery(reservesIds = reserveRecords.map { it.remainId }))
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
                fullDocument.documentDb.structuralFromId,
                mutableListOf()
            ).orEmpty()

        val structuralToIds =
            structuralSyncApi.getStructuralFullPath(
                fullDocument.documentDb.structuralToId,
                mutableListOf()
            ).orEmpty()

        return fullDocument.documentDb.toDocumentSyncEntity(
            mol = fullDocument.molDb?.toSyncEntity(),
            exploiting = fullDocument.exploitingDb?.toSyncEntity(),
            accountingObjects = accountingObjects,
            reserves = reserves,
            locationFrom = locationSyncApi.getLocationById(fullDocument.documentDb.locationFromId),
            locationTo = locationSyncApi.getLocationById(fullDocument.documentDb.locationToId),
            structuralToSyncEntity = structuralToIds,
            structuralFromSyncEntity = structuralFromIds,
            actionBase = fullDocument.actionBaseDb?.toSyncEntity()
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
        val existRecords = actionRecordDao.getAll(
            sqlActionRecordQuery(
                actionId = actionId,
                accountingObjectIds = accountingObjectIds
            )
        )
        val newRecords = accountingObjectIds.map { accountingObjectId ->
            val existRecord = existRecords.find { it.accountingObjectId == accountingObjectId }
            ActionRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                accountingObjectId = accountingObjectId,
                actionId = actionId,
                insertDate = existRecord?.insertDate,
                updateDate = System.currentTimeMillis(),
                userInserted = existRecord?.userInserted ?: userUpdated,
                userUpdated = userUpdated
            )
        }
        actionRecordDao.insertAll(newRecords)
    }

    private suspend fun updateRemainsActionRecords(
        remainIds: List<DocumentReserveCountSyncEntity>,
        actionId: String,
        userUpdated: String?
    ) {
        val existRecords = actionRemainsRecordDao.getAll(
            sqlActionRemainsRecordQuery(
                actionId = actionId,
                remainIds = remainIds.map { it.id }
            )
        )
        val newRecords = remainIds.map { remain ->
            val existRecord = existRecords.find { it.remainId == remain.id }
            ActionRemainsRecordDb(
                id = existRecord?.id ?: UUID.randomUUID().toString(),
                remainId = remain.id,
                actionId = actionId,
                updateDate = System.currentTimeMillis(),
                insertDate = existRecord?.insertDate,
                count = remain.count,
                userUpdated = userUpdated,
                userInserted = existRecord?.userInserted ?: userUpdated
            )
        }
        actionRemainsRecordDao.insertAll(newRecords)
    }

    private suspend fun List<String?>?.getLocations(): List<LocationSyncEntity> {
        return this?.let { locationSyncApi.getLocationsByIds(it) }.orEmpty()
    }
}