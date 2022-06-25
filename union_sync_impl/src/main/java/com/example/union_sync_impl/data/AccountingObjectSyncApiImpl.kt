package com.example.union_sync_impl.data

import com.example.union_sync_api.data.AccountingObjectSyncApi
import com.example.union_sync_api.entity.AccountingObjectDetailSyncEntity
import com.example.union_sync_api.entity.AccountingObjectSyncEntity
import com.example.union_sync_api.entity.LocationSyncEntity
import com.example.union_sync_impl.dao.AccountingObjectDao
import com.example.union_sync_impl.dao.DepartmentDao
import com.example.union_sync_impl.dao.EmployeeDao
import com.example.union_sync_impl.dao.LocationDao
import com.example.union_sync_impl.dao.LocationPathDao
import com.example.union_sync_impl.dao.NomenclatureDao
import com.example.union_sync_impl.dao.NomenclatureGroupDao
import com.example.union_sync_impl.dao.OrganizationDao
import com.example.union_sync_impl.dao.ProviderDao
import com.example.union_sync_impl.dao.sqlAccountingObjectQuery
import com.example.union_sync_impl.data.mapper.toAccountingObjectDb
import com.example.union_sync_impl.data.mapper.toAccountingObjectDetailSyncEntity
import com.example.union_sync_impl.data.mapper.toDepartmentDb
import com.example.union_sync_impl.data.mapper.toEmployeeDb
import com.example.union_sync_impl.data.mapper.toLocationDb
import com.example.union_sync_impl.data.mapper.toLocationSyncEntity
import com.example.union_sync_impl.data.mapper.toNomenclatureDb
import com.example.union_sync_impl.data.mapper.toNomenclatureGroupDb
import com.example.union_sync_impl.data.mapper.toOrganizationDb
import com.example.union_sync_impl.data.mapper.toProviderDb
import com.example.union_sync_impl.data.mapper.toSyncEntity
import com.example.union_sync_impl.entity.location.LocationDb
import com.example.union_sync_impl.entity.location.LocationTypeDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.openapitools.client.custom_api.AccountingObjectApi
import org.openapitools.client.models.CustomAccountingObjectDto
import org.openapitools.client.models.GetAllResponse

class AccountingObjectSyncApiImpl(
    private val api: AccountingObjectApi,
    private val accountingObjectsDao: AccountingObjectDao,
    private val organizationsDao: OrganizationDao,
    private val employeeDao: EmployeeDao,
    private val nomenclatureDao: NomenclatureDao,
    private val nomenclatureGroupDao: NomenclatureGroupDao,
    private val locationPathDao: LocationPathDao,
    private val locationDao: LocationDao,
    private val departmentDao: DepartmentDao,
    private val providerDao: ProviderDao
) : AccountingObjectSyncApi {

    override suspend fun getAccountingObjects(
        organizationId: String?,
        exploitingId: String?,
        textQuery: String?
    ): Flow<List<AccountingObjectSyncEntity>> {
        return flow {
            emit(getDbData(organizationId, exploitingId))
            syncAccountingObjects()
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

    private suspend fun apiAccountingObjectsGet(): GetAllResponse<CustomAccountingObjectDto> {
        return api.apiAccountingObjectsGet()
    }

    private suspend fun syncAccountingObjects() {
        apiAccountingObjectsGet().list?.let { objects ->
            organizationsDao.insertAll(
                (
                        objects.mapNotNull { it.extendedOrganization?.toOrganizationDb() } +
                                objects.mapNotNull { it.extendedDepartment?.extendedOrganization?.toOrganizationDb() } +
                                objects.mapNotNull { it.extendedMol?.extendedOrganization?.toOrganizationDb() } +
                                objects.mapNotNull { it.extendedExploiting?.extendedOrganization?.toOrganizationDb() }
                        ).distinctBy { it.id }
            )

            employeeDao.insertAll(
                (objects.mapNotNull { it.extendedMol?.toEmployeeDb() } +
                        objects.mapNotNull { it.extendedExploiting?.toEmployeeDb() }).distinctBy { it.id }
            )

            nomenclatureGroupDao.insertAll(
                (
                        objects.mapNotNull { it.extendedNomenclatureGroup?.toNomenclatureGroupDb() } +
                                objects.mapNotNull { it.extendedNomenclature?.extendedNomenclatureGroup?.toNomenclatureGroupDb() }
                        ).distinctBy { it.id }
            )

            nomenclatureDao.insertAll(objects.mapNotNull { it.extendedNomenclature?.toNomenclatureDb() })
            locationDao.insertAll(objects.mapNotNull { it.extendedLocation?.toLocationDb() })
            departmentDao.insertAll(objects.mapNotNull { it.extendedDepartment?.toDepartmentDb() })
            providerDao.insertAll(objects.mapNotNull { it.extendedProvider?.toProviderDb() })
            accountingObjectsDao.insertAll(objects.map { it.toAccountingObjectDb() })
        }
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