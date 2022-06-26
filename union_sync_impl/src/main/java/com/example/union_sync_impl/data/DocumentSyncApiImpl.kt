package com.example.union_sync_impl.data

import com.example.union_sync_api.data.DocumentSyncApi
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.DocumentCreateSyncEntity
import com.example.union_sync_api.entity.DocumentSyncEntity
import com.example.union_sync_api.entity.DocumentUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.DocumentDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.dao.sqlDocumentsQuery
import com.example.union_sync_impl.data.mapper.toDocumentDb
import com.example.union_sync_impl.data.mapper.toDocumentSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationShortSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DocumentSyncApiImpl(
    private val documentDao: DocumentDao,
    private val locationDao: LocationDao,
    private val accountingObjectDao: AccountingObjectDao
) : DocumentSyncApi {
    override suspend fun createDocument(documentCreateSyncEntity: DocumentCreateSyncEntity): Long {
        return documentDao.insert(documentCreateSyncEntity.toDocumentDb())
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


    override suspend fun getDocumentById(id: Long): DocumentSyncEntity {
        val fullDocument = documentDao.getDocumentById(id)
        val locationIds = fullDocument.documentDb.locationIds
        val locations = if (locationIds != null) {
            locationDao.getLocationsByIds(fullDocument.documentDb.locationIds).map {
                it.toLocationShortSyncEntity()
            }
        } else {
            null
        }

        val accountingObjects: List<AccountingObjectSyncEntity> =
            accountingObjectDao.getAll(sqlAccountingObjectQuery(accountingObjectsIds = fullDocument.documentDb.accountingObjectsIds))
                .map {
                    it.toSyncEntity(getLocationSyncEntity(it.locationDb))
                }

        return fullDocument.documentDb.toDocumentSyncEntity(
            organizationSyncEntity = fullDocument.organizationDb?.toSyncEntity(),
            mol = fullDocument.molDb?.toSyncEntity(),
            exploiting = fullDocument.exploitingDb?.toSyncEntity(),
            locations = locations,
            accountingObjects = accountingObjects
        )
    }

    override suspend fun updateDocument(documentUpdateSyncEntity: DocumentUpdateSyncEntity) {
        documentDao.update(documentUpdateSyncEntity.toDocumentDb())
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
}