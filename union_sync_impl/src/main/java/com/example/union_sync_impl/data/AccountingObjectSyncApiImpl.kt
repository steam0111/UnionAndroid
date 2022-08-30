package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.data.LocationSyncApi
import com.example.union_sync_api.data.StructuralSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectScanningData
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.AccountingObjectUpdateSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.StructuralDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toAccountingObjectScanningUpdate
import com.example.union_sync_impl.data.mapper.toAccountingObjectUpdate
import com.example.union_sync_impl.data.mapper.toStructuralSyncEntity
import com.example.union_sync_impl.data.mapper.toSyncEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountingObjectSyncApiImpl(
    private val accountingObjectsDao: AccountingObjectDao,
    private val locationSyncApi: LocationSyncApi,
    private val structuralSyncApi: StructuralSyncApi
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
                locationIds = locationIds,
                structuralIds = structuralId
            )
        ).map {
            val location = locationSyncApi.getLocationById(it.accountingObjectDb.locationId)
            it.toSyncEntity(locationSyncEntity = listOfNotNull(location))
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
                locationIds = locationIds,
                structuralIds = structuralIds
            )
        )
    }

    override suspend fun getAccountingObjectDetailById(id: String): AccountingObjectDetailSyncEntity {
        val fullAccountingObjectDb = accountingObjectsDao.getById(id)
        val location =
            locationSyncApi.getLocationFullPath(fullAccountingObjectDb.accountingObjectDb.locationId)

        val structurals =
            structuralSyncApi.getStructuralFullPath(
                fullAccountingObjectDb.structuralDb?.id,
                mutableListOf()
            ).orEmpty()
        val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }.takeIf { it >= 0 } ?: 0
        val balanceUnit = structurals.subList(0, balanceUnitIndex)

        return fullAccountingObjectDb.toAccountingObjectDetailSyncEntity(
            location,
            balanceUnit,
            structurals
        )
    }

    override suspend fun getAccountingObjectDetailByIdFlow(id: String): Flow<AccountingObjectDetailSyncEntity> {
        return accountingObjectsDao.getByIdFlow(id).map {
            val location =
                locationSyncApi.getLocationFullPath(it.accountingObjectDb.locationId)

            val structurals =
                structuralSyncApi.getStructuralFullPath(it.structuralDb?.id, mutableListOf())
                    .orEmpty()
            val balanceUnitIndex = structurals.indexOfLast { it.balanceUnit }
            val balanceUnit = structurals.subList(0, balanceUnitIndex + 1)

            it.toAccountingObjectDetailSyncEntity(
                location,
                balanceUnit,
                structurals
            )
        }
    }

    override suspend fun updateAccountingObjects(accountingObjects: List<AccountingObjectUpdateSyncEntity>) {
        accountingObjectsDao.update(accountingObjects.map { it.toAccountingObjectUpdate() })
    }

    override suspend fun updateAccountingObjectScanningData(accountingObject: AccountingObjectScanningData) {
        accountingObjectsDao.update(accountingObject.toAccountingObjectScanningUpdate())
    }
}