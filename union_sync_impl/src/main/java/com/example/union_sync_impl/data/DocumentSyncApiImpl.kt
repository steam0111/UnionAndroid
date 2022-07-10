package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentReserveCountSyncApi
import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.entity.AccountingObjectInfoSyncEntity
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
import com.example.union_sync_impl.dao.DocumentReserveCountDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.ReserveDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlActionRecordQuery
import com.example.union_sync_impl.dao.sqlActionRemainsRecordQuery
import com.example.union_sync_impl.dao.sqlDocumentReserveCountQuery
import com.example.union_sync_impl.dao.sqlDocumentReserveCountQuery
import com.example.union_sync_impl.dao.sqlDocumentsQuery
import com.example.union_sync_impl.dao.sqlReserveQuery
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDocumentSyncEntity
import com.example.union_sync_impl.data.mapper.toDocumentUpdateReserves
import com.example.union_sync_impl.data.mapper.toLocationShortSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.ActionRecordDb
import com.example.union_sync_impl.entity.ActionRemainsRecordDb
import com.example.union_sync_impl.entity.InventoryRecordDb
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DocumentSyncApiImpl(
    private val documentDao: DocumentDao,
    private val locationDao: LocationDao,
    private val accountingObjectDao: AccountingObjectDao,
    private val reserveDao: ReserveDao,
    private val documentReserveCountSyncApi: DocumentReserveCountSyncApi,
    private val actionRecordDao: ActionRecordDao,
    private val actionRemainsRecordDao: ActionRemainsRecordDao
) : DocumentSyncApi {
    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): String {
        val documentId = UUID.randomUUID().toString()
        documentDao.insert(documentCreateSyncEntity.toDocumentDb(documentId))
        updateActionRecords(
            accountingObjectIds = documentCreateSyncEntity.accountingObjectsIds.orEmpty(),
            actionId = documentId
        )
        updateRemainsActionRecords(
            remainIds = documentCreateSyncEntity.reservesIds.orEmpty(),
            actionId = documentId
        )
        return documentId
    }

    override suspend fun getAllDocuments(
        textQuery: String?,
        molId: String?,
        exploitingId: String?,
        organizationId: String?
    ): Flow<List<DocumentSyncEntity>> {
        return documentDao.getAll(
            sqlDocumentsQuery(
                textQuery = textQuery,
                molId = molId,
                exploitingId = exploitingId,
                organizationId = organizationId
            )
        ).map {
            it.map {
                it.documentDb.toDocumentSyncEntity(
                    organizationSyncEntity = it.organizationDb?.toSyncEntity(),
                    mol = it.molDb?.toSyncEntity(),
                    exploiting = it.exploitingDb?.toSyncEntity(),
                    locations = null,
                    accountingObjects = listOf()
                )
            }
        }
    }

    override suspend fun getDocuments(
        type: String,
        textQuery: String?
    ): Flow<List<DocumentSyncEntity>> {
        return documentDao.getDocumentsByType(type).map {
            it.map {
                it.documentDb.toDocumentSyncEntity(
                    organizationSyncEntity = it.organizationDb?.toSyncEntity(),
                    mol = it.molDb?.toSyncEntity(),
                    exploiting = it.exploitingDb?.toSyncEntity(),
                    locations = null,
                    accountingObjects = listOf()
                )
            }
        }
    }


    override suspend fun getDocumentById(id: String): DocumentSyncEntity {
        val fullDocument = documentDao.getDocumentById(id)
        val locationIds = fullDocument.documentDb.locationIds
        val locations = if (locationIds != null) {
            locationDao.getLocationsByIds(fullDocument.documentDb.locationIds).map {
                it.toLocationShortSyncEntity()
            }
        } else {
            null
        }

        val accountingObjectIds =
            actionRecordDao.getAll(sqlActionRecordQuery(id)).map { it.accountingObjectId }
        val accountingObjects: List<AccountingObjectSyncEntity> =
            accountingObjectDao.getAll(
                sqlAccountingObjectQuery(
                    accountingObjectsIds = accountingObjectIds
                )
            )
                .map {
                    it.toSyncEntity(getLocationSyncEntity(it.locationDb))
                }

        val reserveIds =
            actionRemainsRecordDao.getAll((sqlActionRemainsRecordQuery(id))).map { it.remainId }

        var reserves: List<ReserveSyncEntity> =
            reserveDao.getAll(sqlReserveQuery(reservesIds = reserveIds))
                .map { it.toSyncEntity(getLocationSyncEntity(it.locationDb)) }

        val documentReservesCounts: List<DocumentReserveCountSyncEntity> =
            documentReserveCountSyncApi.getAll(reserves.map { it.id })

        reserves = reserves.map { reserve ->
            val documentReserveCount = documentReservesCounts.find { it.id == reserve.id }
            if (documentReserveCount != null) {
                reserve.copy(count = documentReserveCount.count)
            } else {
                reserve
            }
        }

        return fullDocument.documentDb.toDocumentSyncEntity(
            organizationSyncEntity = fullDocument.organizationDb?.toSyncEntity(),
            mol = fullDocument.molDb?.toSyncEntity(),
            exploiting = fullDocument.exploitingDb?.toSyncEntity(),
            locations = locations,
            accountingObjects = accountingObjects,
            reserves = reserves
        )
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        documentDao.update(documentUpdateSyncEntity.toDocumentDb())
        updateActionRecords(
            accountingObjectIds = documentUpdateSyncEntity.accountingObjectsIds.orEmpty(),
            actionId = documentUpdateSyncEntity.id
        )
        updateRemainsActionRecords(
            remainIds = documentUpdateSyncEntity.reservesIds.orEmpty(),
            actionId = documentUpdateSyncEntity.id
        )
    }

    override suspend fun updateDocumentReserves(documentUpdateReservesSyncEntity: DocumentUpdateReservesSyncEntity) {
        updateRemainsActionRecords(
            remainIds = documentUpdateReservesSyncEntity.reservesIds,
            actionId = documentUpdateReservesSyncEntity.id
        )
    }

    //TODO переделать на join
    private suspend fun getLocationSyncEntity(locationDb: LocationDb?): LocationSyncEntity? {
        if (locationDb == null) {
            return null
        }
        val locationTypeId = locationDb.locationTypeId ?: return null
        val locationTypeDb: LocationTypeDb =
            locationDao.getLocationTypeById(locationTypeId) ?: return null

        return locationDb.toLocationSyncEntity(locationTypeDb)
    }

    private suspend fun updateActionRecords(
        accountingObjectIds: List<String>,
        actionId: String
    ) {
        val existRecords = actionRecordDao.getAll(
            sqlActionRecordQuery(
                actionId = actionId,
                accountingObjectIds = accountingObjectIds
            )
        )
        val newRecords = accountingObjectIds.filter { accountingObjectId ->
            val existRecord = existRecords.find { it.accountingObjectId == accountingObjectId }
            existRecord == null
        }.map { accountingObjectId ->
            ActionRecordDb(
                id = UUID.randomUUID().toString(),
                accountingObjectId = accountingObjectId,
                actionId = actionId,
                updateDate = System.currentTimeMillis()
            )
        }
        actionRecordDao.insertAll(newRecords)
    }

    private suspend fun updateRemainsActionRecords(
        remainIds: List<String>,
        actionId: String
    ) {
        val existRecords = actionRemainsRecordDao.getAll(
            sqlActionRemainsRecordQuery(
                actionId = actionId,
                remainIds = remainIds
            )
        )
        val newRecords = remainIds.filter { remainId ->
            val existRecord = existRecords.find { it.remainId == remainId }
            existRecord == null
        }.map { remainId ->
            ActionRemainsRecordDb(
                id = UUID.randomUUID().toString(),
                remainId = remainId,
                actionId = actionId,
                updateDate = System.currentTimeMillis()
            )
        }
        actionRemainsRecordDao.insertAll(newRecords)
    }
}