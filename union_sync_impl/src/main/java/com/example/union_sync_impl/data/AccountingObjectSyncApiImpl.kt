package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountingObjectSyncApiImpl(
    private val accountingObjectsDao: AccountingObjectDao,
    private val locationDao: LocationDao,
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(
        organizationId: String?,
        exploitingId: String?,
        textQuery: String?
    ): Flow<List<AccountingObjectSyncEntity>> {
        return flow {
            emit(getDbData(organizationId, exploitingId))
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity {
        val fullAccountingObjectDb = accountingObjectsDao.getById(id)
        val location = getLocationSyncEntity(fullAccountingObjectDb.locationDb)
        return fullAccountingObjectDb.toAccountingObjectDetailSyncEntity(location)

    }

    override suspend fun getAccountingObjectsByRfids(accountingObjectRfids: List<String>): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll(sqlAccountingObjectQuery(rfids = accountingObjectRfids))
            .map {
                it.toSyncEntity(
                    locationSyncEntity = getLocationSyncEntity(it.locationDb)
                )
            }
    }

    override suspend fun getAccountingObjectsByBarcode(accountingObjectBarcode: String): AccountingObjectSyncEntity? {
        val fullAccountingObjects =
            accountingObjectsDao.getAll(sqlAccountingObjectQuery(barcode = accountingObjectBarcode))
        val fullAccountingObject = fullAccountingObjects.firstOrNull()
        return fullAccountingObject?.toSyncEntity(
            locationSyncEntity = getLocationSyncEntity(fullAccountingObject.locationDb)
        )
    }

    private suspend fun getDbData(
        organizationId: String? = null,
        exploitingId: String? = null,
        accountingObjectsIds: List<String>? = null,
        textQuery: String? = null
    ): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll(
            sqlAccountingObjectQuery(
                organizationId = organizationId,
                exploitingId = exploitingId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery
            )
        )
            .map {
                it.toSyncEntity(
                    locationSyncEntity = getLocationSyncEntity(it.locationDb)
                )
            }

    }

    //TODO переделать на join
    private suspend fun getLocationSyncEntity(locationDb: LocationDb?): LocationSyncEntity? {
        if (locationDb == null) {
            return null
        }
        val locationTypeId = locationDb.locationTypeId ?: return null
        val locationTypeDb: LocationTypeDb = locationDao.getLocationTypeById(locationTypeId) ?: return null

        return locationDb.toLocationSyncEntity(locationTypeDb)
    }
}