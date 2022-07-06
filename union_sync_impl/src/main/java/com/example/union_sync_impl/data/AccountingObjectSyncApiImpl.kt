package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toAccountingObjectUpdate
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb

class AccountingObjectSyncApiImpl(
    private val accountingObjectsDao: AccountingObjectDao,
    private val locationDao: LocationDao,
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(
        organizationId: String?,
        exploitingId: String?,
        molId: String?,
        departmentId: String?,
        producerId: String?,
        equipmentTypeId: String?,
        providerId: String?,
        rfids: List<String>?,
        barcode: String?,
        statusId: String?,
        textQuery: String?,
        accountingObjectsIds: List<String>?
    ): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll(
            sqlAccountingObjectQuery(
                organizationId = organizationId,
                exploitingId = exploitingId,
                producerId = producerId,
                providerId = providerId,
                equipmentTypeId = equipmentTypeId,
                departmentId = departmentId,
                molId = molId,
                rfids = rfids,
                barcode = barcode,
                statusId = statusId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery
            )
        ).map {
            it.toSyncEntity(
                locationSyncEntity = getLocationSyncEntity(it.locationDb)
            )
        }
    }

    override suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity {
        val fullAccountingObjectDb = accountingObjectsDao.getById(id)
        val location = getLocationSyncEntity(fullAccountingObjectDb.locationDb)
        return fullAccountingObjectDb.toAccountingObjectDetailSyncEntity(location)
    }

    override suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>) {
        accountingObjectsDao.update(accountingObjects.map { it.toAccountingObjectUpdate() })
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