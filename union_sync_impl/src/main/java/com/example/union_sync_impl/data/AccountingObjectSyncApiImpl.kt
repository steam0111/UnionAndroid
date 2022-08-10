package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toAccountingObjectUpdate
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity

class AccountingObjectSyncApiImpl(
    private val accountingObjectsDao: AccountingObjectDao,
    private val locationSyncApi: LocationSyncApi,
    private val structuralDao: StructuralDao
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(
        exploitingId: String?,
        molId: String?,
        producerId: String?,
        equipmentTypeId: String?,
        providerId: String?,
        rfids: List<String>?,
        barcode: String?,
        statusId: String?,
        textQuery: String?,
        accountingObjectsIds: List<String>?,
        locationIds: List<String?>?,
        structuralId: List<String?>?
    ): List<AccountingObjectSyncEntity> {
        return accountingObjectsDao.getAll(
            sqlAccountingObjectQuery(
                exploitingId = exploitingId,
                producerId = producerId,
                providerId = providerId,
                equipmentTypeId = equipmentTypeId,
                molId = molId,
                rfids = rfids,
                barcode = barcode,
                statusId = statusId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery,
                locationIds = locationIds
            )
        ).map {
            val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
            it.toSyncEntity(locationSyncEntity = location)
        }
    }

    override suspend fun getAccountingObjectsCount(
        exploitingId: String?,
        molId: String?,
        producerId: String?,
        equipmentTypeId: String?,
        providerId: String?,
        rfids: List<String>?,
        barcode: String?,
        statusId: String?,
        textQuery: String?,
        accountingObjectsIds: List<String>?,
        locationIds: List<String?>?,
        structuralIds: List<String?>?
    ): Long {
        return accountingObjectsDao.getCount(
            sqlAccountingObjectQuery(
                exploitingId = exploitingId,
                producerId = producerId,
                providerId = providerId,
                equipmentTypeId = equipmentTypeId,
                molId = molId,
                rfids = rfids,
                barcode = barcode,
                statusId = statusId,
                accountingObjectsIds = accountingObjectsIds,
                textQuery = textQuery,
                isFilterCount = true,
                locationIds = locationIds
            )
        )
    }

    override suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity {
        val fullAccountingObjectDb = accountingObjectsDao.getById(id)
        val location =
            locationSyncApi.getLocationById(fullAccountingObjectDb.accountingObjectDb.locationId)
        val balanceUnit = structuralDao.getAllStructuralsByChildId(fullAccountingObjectDb.structuralDb?.id)
                .firstOrNull { it.balanceUnit == true }
        return fullAccountingObjectDb.toAccountingObjectDetailSyncEntity(location, balanceUnit?.toStructuralSyncEntity())
    }

    override suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>) {
        accountingObjectsDao.update(accountingObjects.map { it.toAccountingObjectUpdate() })
    }
}